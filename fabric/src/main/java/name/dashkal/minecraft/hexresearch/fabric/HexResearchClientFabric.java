package name.dashkal.minecraft.hexresearch.fabric;

import name.dashkal.minecraft.hexresearch.fabric.client.network.NetworkClientFabric;
import net.fabricmc.api.ClientModInitializer;
import name.dashkal.minecraft.hexresearch.HexResearchClient;

/**
 * Fabric client loading entrypoint.
 */
public class HexResearchClientFabric implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        NetworkClientFabric.init();
        HexResearchClient.init();
    }
}
