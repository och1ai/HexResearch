package name.dashkal.minecraft.hexresearch.network;

import at.petrak.hexcasting.common.entities.EntityWallScroll;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;

public class Networking {
    public static void init() {
    }

    public static void sendScrollSync(EntityWallScroll entity, ServerLevel level) {
        ScrollSyncPacket packet = new ScrollSyncPacket(entity.getId(), entity.scroll);

        for (ServerPlayer player : level.players()) {
            packet.sendToPlayer(player);
        }
    }

    public static void sendMindImpression(ServerLevel level, Entity source, BlockPos inducerPos) {
        MindImpressionPacket packet = new MindImpressionPacket(level.dimension().location(), source.getId(), inducerPos);

        for (ServerPlayer player : level.players()) {
            packet.sendToPlayer(player);
        }
    }
}
