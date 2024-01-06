package name.dashkal.minecraft.hexresearch;

import name.dashkal.minecraft.hexresearch.client.network.NetworkClient;
import name.dashkal.minecraft.hexresearch.client.scrying.ScryingLensOverlays;

/**
 * Common client loading entrypoint.
 */
public class HexResearchClient {
    public static void init() {
        NetworkClient.init();
        ScryingLensOverlays.init();
    }
}
