package name.dashkal.minecraft.hexresearch;

import name.dashkal.minecraft.hexresearch.client.network.NetworkClient;

/**
 * Common client loading entrypoint.
 */
public class HexResearchClient {
    public static void init() {
        NetworkClient.init();
    }
}
