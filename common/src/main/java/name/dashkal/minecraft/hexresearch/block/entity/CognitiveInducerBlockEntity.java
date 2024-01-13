package name.dashkal.minecraft.hexresearch.block.entity;

import at.petrak.hexcasting.api.misc.MediaConstants;
import at.petrak.hexcasting.common.misc.Brainsweeping;
import name.dashkal.minecraft.hexresearch.HexResearch;
import name.dashkal.minecraft.hexresearch.client.particles.HRParticleUtils;
import name.dashkal.minecraft.hexresearch.config.ServerConfig;
import name.dashkal.minecraft.hexresearch.network.Networking;
import name.dashkal.minecraft.hexresearch.registry.HRBlockEntities;
import name.dashkal.minecraft.hexresearch.util.Mind;
import name.dashkal.minecraft.hexresearch.util.MindImpressions;
import name.dashkal.minecraft.hexresearch.xplat.XPlatAPI;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.SortedSet;
import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 * Block Entity logic for the Cognitive Inducer
 */
public class CognitiveInducerBlockEntity extends AbstractMediaContainerBlockEntity {
    public static final String TAG_IMPRESSIONS = "impressions";

    /** Range of blocks the inducer will search for villagers. Spherical radius. */
    public static final float IMPRESSION_RANGE = 5f;

    /** Cool-down in game ticks before a villager may impress on an inducer again. */
    public static final int IMPRESSION_COOLDOWN_SECONDS = 30;

    private final Logger logger = HexResearch.LOGGER;

    private MindImpressions impressions = new MindImpressions();

    public CognitiveInducerBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(HRBlockEntities.ARTIFICIAL_MIND.get(), blockPos, blockState);
    }

    /** Calculate the induced mind from the impressions. */
    public Optional<Mind> getImpressedMind() {
        return impressions.getImpressedMind();
    }

    /** Removes all impressions from this inducer. */
    public void clearMind() {
        this.impressions.clear();
        updated();
    }

    /** Add a single impression to this inducer. */
    public void impressMind(@Nonnull ResourceLocation profession, @Nonnull ResourceLocation biome, @Nonnegative int rank) {
        this.impressions.impressMind(new Mind(profession, biome, rank));
        updated();
    }

    /**
     * Forcibly sets the mind in this inducer.
     * <p>
     * In practice this means clearing the current impressions, then adding a very large number of impressions for the specified mind.
     */
    public void forceMind(@Nonnull ResourceLocation profession, @Nonnull ResourceLocation biome, @Nonnegative int rank) {
        this.impressions.clear();
        // Using short's max value so that we don't set ourselves up for an overflow
        this.impressions.impressMind(new Mind(profession, biome, rank), Short.MAX_VALUE);
        updated();
    }

    /**
     * Returns a comparator signal from 0-5 indicating the rank of the impressed mind.
     */
    public int getAnalogOutputSignal() {
        return getImpressedMind().map(Mind::rank).orElse(0);
    }

    /** Returns the current amount of media stored in the inducer in raw units. */
    public int getCurrentMedia() {
        return mediaContainer.getCurrentMedia();
    }

    @Override
    protected void onMediaContainerUpdated() {
        updated();
    }

    /**
     * Spawn particles indicating a mind impression was taken.
     *
     * @param level the world to spawn the particles in
     * @param villager the villager the particles should spawn from
     * @param inducerPos the position of the cognitive inducer taking the impression
     * @param successful if <code>true</code>, spawn mind colored particles.
     *                   if <code>false</code>, spawn dark grey particles indicating failure.
     */
    public void impressionParticles(Level level, Villager villager, BlockPos inducerPos, boolean successful) {
        int[] colorGradient;

        if (successful) {
            // Color from the villager's mind for successful impressions
            colorGradient = Mind.fromVillager(villager).getColorGradient();
        } else {
            // Dark grey for failed impressions
            colorGradient = new int[] { 0x101010, 0x505050 };
        }

        HRParticleUtils.spawnHexConjuredParticles(
                level,
                villager.getEyePosition(),
                HRParticleUtils.aToBParticleVector(villager.getEyePosition(), Vec3.atCenterOf(inducerPos)),
                colorGradient,
                10
        );
    }

    @SuppressWarnings("unused")
    public void randomTick(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, RandomSource randomSource) {
        takeImpressions(serverLevel, blockPos, randomSource);
    }

    public void tick(@Nonnull Level level, @Nonnull BlockPos blockPos) {
        if (level.getGameTime() % 2 == 0) {
            getImpressedMind().ifPresent(mind -> {
                HRParticleUtils.spawnHexConjuredParticles(level, Vec3.atCenterOf(blockPos), Vec3.ZERO, mind.getColorGradient(), mind.rank());
            });
        }
    }

    @Override
    public void load(@Nonnull CompoundTag compoundTag) {
        super.load(compoundTag);
        impressions = MindImpressions.load(compoundTag.getList(TAG_IMPRESSIONS, Tag.TAG_COMPOUND));
    }

    @Override
    protected void saveAdditional(CompoundTag compoundTag) {
        compoundTag.put(TAG_IMPRESSIONS, impressions.save());
        super.saveAdditional(compoundTag);
    }

    @Nonnull
    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag tag = super.getUpdateTag();
        saveAdditional(tag);
        return tag;
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    private void updated() {
        setChanged();
        Level level = getLevel();
        if (level != null) {
            level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 2);
        }
    }

    private void takeImpressions(ServerLevel serverLevel, BlockPos blockPos, RandomSource randomSource) {
        logger.debug("CognitiveInducerBlockEntity.takeImpressions()");

        // Collect all living villagers in range
        List<Villager> nearbyVillagers = findNearbyVillagers(serverLevel, blockPos, randomSource);

        logger.debug("CognitiveInducerBlockEntity.takeImpressions(): Found {} villagers", nearbyVillagers.size());

        if (nearbyVillagers.isEmpty()) {
            // Nothing nearby, fast-fail.
            return;
        }

        Stream<Villager> imprintsFrom = nearbyVillagers.stream()
                .limit(limitImpressions(nearbyVillagers.size(), randomSource));

        // If we found any
        imprintsFrom.forEach(takeImpression(serverLevel, blockPos));
    }

    private List<Villager> findNearbyVillagers(ServerLevel serverLevel, BlockPos blockPos, RandomSource randomSource) {
        // Build our search bounding box
        Vec3 blockPosF = Vec3.atCenterOf(blockPos);
        AABB bounds = new AABB(
                blockPosF.add(new Vec3(-IMPRESSION_RANGE, -IMPRESSION_RANGE, -IMPRESSION_RANGE)),
                blockPosF.add(new Vec3(IMPRESSION_RANGE, IMPRESSION_RANGE, IMPRESSION_RANGE))
        );

        logger.debug("CognitiveInducerBlockEntity.findNearbyVillagers(): Searching space {}", bounds);

        // Collect all living villagers in range
        List<Villager> villagers = new LinkedList<>(serverLevel.getEntitiesOfClass(Villager.class, bounds, v ->
                v.distanceToSqr(blockPosF) <= (IMPRESSION_RANGE * IMPRESSION_RANGE)
                        && v.isAlive()
                        && !Brainsweeping.isBrainswept(v)
                        && !isRecentlyImpressed(v, serverLevel.getGameTime())
        ));
        shuffleList(villagers, randomSource);
        return villagers;
    }

    private int limitImpressions(int candidates, RandomSource randomSource) {
        if (candidates > 1) {
            // Select a random set with diminishing returns for more villagers
            // We select on average 1 + log2(length - 1)
            double toKeepD = 1d + (Math.log(candidates - 1d) / Math.log(2d));
            int toKeep;
            if (randomSource.nextDouble() < toKeepD - Math.floor(toKeepD)) {
                toKeep = (int) toKeepD + 1;
            } else {
                toKeep = (int) toKeepD;
            }
            logger.debug("CognitiveInducerBlockEntity.limitImpressions(): toKeepD: {}, toKeep: {}", toKeepD, toKeep);

            return toKeep;
        } else {
            return candidates;
        }
    }

    private Consumer<Villager> takeImpression(ServerLevel serverLevel, BlockPos blockPos) {
        ServerConfig.MindTrainingConfig cfg = HexResearch.getServerConfig().mindTrainingConfig();
        return villager -> {
            if (mediaContainer.consumeMedia(cfg.impressionCostDust() * MediaConstants.DUST_UNIT)) {
                // Add a mark to the villager
                markVillager(villager, serverLevel.getGameTime());

                // Last chance to fail - Villager is exhausted due to too many recent marks.
                if (!isExhausted(villager, serverLevel.getGameTime())) {
                    // Impress them into the artificial mind
                    impressMind(
                            Registry.VILLAGER_PROFESSION.getKey(villager.getVillagerData().getProfession()),
                            Registry.VILLAGER_TYPE.getKey(villager.getVillagerData().getType()),
                            villager.getVillagerData().getLevel()
                    );

                    // Send a packet for the particle burst
                    Networking.sendMindImpression(serverLevel, villager, blockPos, true);
                } else {
                    // Send a packet for the particle burst
                    Networking.sendMindImpression(serverLevel, villager, blockPos, false);
                }
            }
        };
    }

    private static void markVillager(Villager villager, long gameTime) {
        XPlatAPI.getInstance().cognitiveInducerMarkVillager(villager, gameTime);
    }

    private static SortedSet<Long> getVillagerMarks(Villager villager, long worldTime) {
        XPlatAPI.getInstance().cognitiveInducerPruneMarks(villager, worldTime);
        return XPlatAPI.getInstance().cognitiveInducerGetMarks(villager);
    }

    private static boolean isRecentlyImpressed(Villager villager, long worldTime) {
        SortedSet<Long> marks = getVillagerMarks(villager, worldTime);
        return !marks.isEmpty() && marks.last() > (worldTime - (IMPRESSION_COOLDOWN_SECONDS * 20L));
    }

    private static boolean isExhausted(Villager villager, long worldTime) {
        int numMarks = getVillagerMarks(villager, worldTime).size();
        int maxMarks = HexResearch.getServerConfig().mindTrainingConfig().requiredRankImpressions().getOrDefault(villager.getVillagerData().getLevel(), Integer.MAX_VALUE);
        return numMarks > maxMarks;
    }

    private static <T> void shuffleList(List<T> list, RandomSource random) {
        if (list.isEmpty()) {
            return;
        }
        int size = list.size();
        for (int i = 0; i < size; i++) {
            int otherIdx = random.nextInt(size);
            T elem = list.get(i);
            list.set(i, list.get(otherIdx));
            list.set(otherIdx, elem);
        }
    }
}
