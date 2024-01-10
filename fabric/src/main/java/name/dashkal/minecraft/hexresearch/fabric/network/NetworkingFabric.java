package name.dashkal.minecraft.hexresearch.fabric.network;

import name.dashkal.minecraft.hexresearch.config.ServerConfig;
import net.minecraft.server.level.ServerPlayer;

public class NetworkingFabric {
    public static void init() {
    }

    public static void sendServerConfig(ServerPlayer player, ServerConfig serverConfig) {
        S2CConfigSyncPacket packet = new S2CConfigSyncPacket(serverConfig);
        packet.sendToPlayer(player);
    }
}
