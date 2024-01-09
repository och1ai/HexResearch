package name.dashkal.minecraft.hexresearch.interop.impl;

import at.petrak.hexcasting.api.spell.casting.CastingContext;
import com.samsthenerd.hexgloop.blocks.IDynamicFlayTarget;
import dev.architectury.platform.Platform;
import name.dashkal.minecraft.hexresearch.HexResearch;
import name.dashkal.minecraft.hexresearch.util.Mind;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.npc.Villager;

import java.util.Optional;

/**
 * Implementation class for HexGloop interoperation.
 * <p>
 * Do not access this class directly. Use HexGloopInterOp in the package above.
 */
public class HexGloopInteropImpl {
    static {
        if (!Platform.isModLoaded("hexgloop")) {
            throw new AssertionError("HexGloopInteropImpl loaded when hexgloop was not present");
        } else {
            HexResearch.LOGGER.info("Initializing HexGloop interop");
        }
    }

    /**
     * Offers a mind to HexGloop (IDynamicFlayTarget) to see if it wants to accept the mind.
     *
     * @param ctx the casting context in which we are imbuing
     * @param blockPos block position of the target block
     * @param mind the mind being offered
     * @return an optional Runnable that if present, will accept the offered mind
     */
    public Optional<Runnable> offerMind(CastingContext ctx, BlockPos blockPos, Mind mind) {
        ServerLevel level = ctx.getWorld();
        if (level.getBlockState(blockPos).getBlock() instanceof IDynamicFlayTarget dft) {
            return offerMindToDynamicFlayTarget(ctx, blockPos, mind, dft);
        } else if (level.getBlockEntity(blockPos) instanceof IDynamicFlayTarget dft) {
            return offerMindToDynamicFlayTarget(ctx, blockPos, mind, dft);
        } else {
            return Optional.empty();
        }
    }

    private Optional<Runnable> offerMindToDynamicFlayTarget(CastingContext ctx, BlockPos blockPos, Mind mind, IDynamicFlayTarget dft) {
        ServerLevel level = ctx.getWorld();
        Villager villager = new Villager(EntityType.VILLAGER, level);
        villager.setVillagerData(
                villager.getVillagerData()
                        .setProfession(Registry.VILLAGER_PROFESSION.get(mind.profession()))
                        .setType(Registry.VILLAGER_TYPE.get(mind.biome()))
                        .setLevel(mind.rank())
        );

        if (dft.canAcceptMind(villager, blockPos, ctx)) {
            return Optional.of(() -> {
                dft.absorbVillagerMind(villager, blockPos, ctx);
                villager.remove(Entity.RemovalReason.DISCARDED);
            });
        } else {
            villager.remove(Entity.RemovalReason.DISCARDED);
            return Optional.empty();
        }
    }
}
