package name.dashkal.minecraft.hexresearch.forge;

import name.dashkal.minecraft.hexresearch.HexResearchClient;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

/**
 * Forge client loading entrypoint.
 */
public class HexResearchClientForge {
    public static void init(FMLClientSetupEvent event) {
        HexResearchClient.init();
    }
}
