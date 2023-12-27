package name.dashkal.minecraft.hexresearch.network;

import at.petrak.hexcasting.common.entities.EntityWallScroll;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;

public class Networking {
    public static void init() {
    }

    public static void sendScrollSync(EntityWallScroll entity, ServerLevel world) {
        ScrollSyncPacket packet = new ScrollSyncPacket(entity.getId(), entity.scroll);

        for (ServerPlayer player : world.players()) {
            packet.sendToPlayer(player);
        }
    }
}
