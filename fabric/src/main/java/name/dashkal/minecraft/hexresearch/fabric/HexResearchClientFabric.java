package name.dashkal.minecraft.hexresearch.fabric;

import net.fabricmc.api.ClientModInitializer;
import name.dashkal.minecraft.hexresearch.HexResearchClient;

/**
 * Fabric client loading entrypoint.
 */
public class HexResearchClientFabric implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        HexResearchClient.init();
    }
}
