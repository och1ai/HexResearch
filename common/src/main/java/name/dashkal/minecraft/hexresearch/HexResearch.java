package name.dashkal.minecraft.hexresearch;

import name.dashkal.minecraft.hexresearch.registry.*;
import name.dashkal.minecraft.hexresearch.network.Networking;
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
        LOGGER.info("Hex Research Initializing");
        LOGGER.debug("Logger name: " + LOGGER.getName());

        // Minecraft / Forge / Fabric Registries
        HexResearchItemRegistry.init();
        HexResearchIotaTypeRegistry.init();
        HexResearchPatternRegistry.init();
        HexResearchEffectRegistry.init();
        HexResearchAdvancementTriggerRegistry.init();

        // Networking
        Networking.init();

        // HexResearch internals
        HexResearchMindHarmRegistry.init();

        //LOGGER.debug(HexResearchAbstractions.getConfigDirectory().toAbsolutePath().normalize().toString());
    }

    /**
     * Shortcut for identifiers specific to this mod.
     */
    public static ResourceLocation id(String string) {
        return new ResourceLocation(MOD_ID, string);
    }
}
