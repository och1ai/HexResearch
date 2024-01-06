package name.dashkal.minecraft.hexresearch.client.network;

import dev.architectury.networking.NetworkManager;
import name.dashkal.minecraft.hexresearch.network.MindImpressionPacket;
import name.dashkal.minecraft.hexresearch.network.ScrollSyncPacket;

public class NetworkClient {
    public static void init() {
        NetworkManager.registerReceiver(NetworkManager.Side.S2C, ScrollSyncPacket.PACKET_ID, ScrollSyncPacket::s2cHandler);
        NetworkManager.registerReceiver(NetworkManager.Side.S2C, MindImpressionPacket.PACKET_ID, MindImpressionPacket::s2cHandler);
    }
}
