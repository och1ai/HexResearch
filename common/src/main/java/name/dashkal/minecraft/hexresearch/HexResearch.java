package name.dashkal.minecraft.hexresearch;

import name.dashkal.minecraft.hexresearch.registry.HexResearchIotaTypeRegistry;
import name.dashkal.minecraft.hexresearch.registry.HexResearchItemRegistry;
import name.dashkal.minecraft.hexresearch.registry.HexResearchPatternRegistry;
import net.minecraft.resources.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * This is effectively the loading entrypoint for most of your code, at least
 * if you are using Architectury as intended.
 */
public class HexResearch {
    public static final String MOD_ID = "hexresearch";
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);


    public static void init() {
        LOGGER.info("Hex Research says hello!");

        HexResearchItemRegistry.init();
        HexResearchIotaTypeRegistry.init();
        HexResearchPatternRegistry.init();

        LOGGER.info(HexResearchAbstractions.getConfigDirectory().toAbsolutePath().normalize().toString());
    }

    /**
     * Shortcut for identifiers specific to this mod.
     */
    public static ResourceLocation id(String string) {
        return new ResourceLocation(MOD_ID, string);
    }
}
