package name.dashkal.minecraft.hexresearch.network;

import dev.architectury.networking.NetworkManager;
import io.netty.buffer.Unpooled;
import name.dashkal.minecraft.hexresearch.HexResearch;
import name.dashkal.minecraft.hexresearch.block.entity.CognitiveInducerBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.level.Level;

public class MindImpressionPacket {
    public static final ResourceLocation PACKET_ID = new ResourceLocation(HexResearch.MOD_ID, "mind_impression");

    private final ResourceLocation dimensionId;
    private final int entityId;
    private final BlockPos inducerPos;

    private final FriendlyByteBuf buf;

    public MindImpressionPacket(ResourceLocation dimensionId, int entityId, BlockPos inducerPos) {
        this.dimensionId = dimensionId;
        this.entityId = entityId;
        this.inducerPos = inducerPos;
        this.buf = new FriendlyByteBuf(Unpooled.buffer());
        this.buf.writeResourceLocation(dimensionId);
        this.buf.writeInt(entityId);
        this.buf.writeBlockPos(inducerPos);
    }

    public ResourceLocation getDimensionId() {
        return dimensionId;
    }

    public int getEntityId() {
        return entityId;
    }

    public BlockPos getInducerPos() {
        return inducerPos;
    }

    public void sendToPlayer(ServerPlayer player) {
        NetworkManager.sendToPlayer(player, PACKET_ID, buf);
    }

    public static void s2cHandler(FriendlyByteBuf buf, NetworkManager.PacketContext context) {
        ResourceLocation dimensionId = buf.readResourceLocation();
        int entityId = buf.readInt();
        BlockPos blockPos = buf.readBlockPos();

        // Find the matching entity
        Level level = Minecraft.getInstance().level;

        if (level != null && level.dimension().location().equals(dimensionId)) {
            Entity entity = Minecraft.getInstance().level.getEntity(entityId);
            if (entity instanceof Villager villager
                    && level.getBlockEntity(blockPos) instanceof CognitiveInducerBlockEntity cibe) {
                // Found it
                cibe.impressionParticles(level, villager, blockPos);
            }
        }
    }
}
