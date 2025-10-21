package name.dashkal.minecraft.hexresearch.forge;

import dev.architectury.platform.forge.EventBuses;
import name.dashkal.minecraft.hexresearch.HexResearch;
import name.dashkal.minecraft.hexresearch.forge.config.HexResearchConfigForgeImpl;
import name.dashkal.minecraft.hexresearch.forge.event.Events;
import name.dashkal.minecraft.hexresearch.forge.xplat.ForgeXPlatAPIImpl;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

/**
 * This is your loading entrypoint on forge, in case you need to initialize
 * something platform-specific.
 */
@Mod(HexResearch.MOD_ID)
public class HexResearchForge {
    public HexResearchForge() {
        System.out.println("[HexResearch] Starting HexResearchForge constructor");
        try {
            // Submit our event bus to let architectury register our content on the right time
            System.out.println("[HexResearch] Getting mod event bus");
            IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
            System.out.println("[HexResearch] Registering mod event bus with Architectury");
            EventBuses.registerModEventBus(HexResearch.MOD_ID, bus);
            System.out.println("[HexResearch] Adding client init listener");
            bus.addListener(HexResearchClientForge::init);
            
            System.out.println("[HexResearch] Adding creative tab listener");
            bus.addListener((net.minecraftforge.event.BuildCreativeModeTabContentsEvent evt) -> {
                if (evt.getTabKey().equals(ResourceKey.create(
                    Registries.CREATIVE_MODE_TAB,
                    at.petrak.hexcasting.api.HexAPI.modLoc("hexcasting")
                ))) {
                    evt.accept(name.dashkal.minecraft.hexresearch.registry.HRBlocks.COGNITIVE_INDUCER.get());
                }
            });

            System.out.println("[HexResearch] Initializing ForgeXPlatAPIImpl");
            ForgeXPlatAPIImpl.init();
            System.out.println("[HexResearch] Initializing HexResearchConfigForgeImpl");
            HexResearchConfigForgeImpl.init();
            System.out.println("[HexResearch] Calling HexResearch.init()");
            HexResearch.init();
            System.out.println("[HexResearch] Initializing Events");
            Events.init();
            System.out.println("[HexResearch] HexResearchForge constructor completed successfully");
        } catch (Exception e) {
            System.err.println("[HexResearch] ERROR in HexResearchForge constructor: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }
}
