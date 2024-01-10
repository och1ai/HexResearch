package name.dashkal.minecraft.hexresearch.fabric.client.network;

import dev.architectury.networking.NetworkManager;
import name.dashkal.minecraft.hexresearch.fabric.network.S2CConfigSyncPacket;

public class NetworkClientFabric {
    public static void init() {
        NetworkManager.registerReceiver(NetworkManager.Side.S2C, S2CConfigSyncPacket.PACKET_ID, S2CConfigSyncPacket::s2cHandler);
    }
}
