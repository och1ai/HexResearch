package name.dashkal.minecraft.hexresearch.forge;

import dev.architectury.platform.forge.EventBuses;
import name.dashkal.minecraft.hexresearch.HexResearch;
import name.dashkal.minecraft.hexresearch.forge.config.HexResearchConfigForgeImpl;
import name.dashkal.minecraft.hexresearch.forge.event.Events;
import name.dashkal.minecraft.hexresearch.forge.xplat.ForgeXPlatAPIImpl;
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
        // Submit our event bus to let architectury register our content on the right time
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        EventBuses.registerModEventBus(HexResearch.MOD_ID, bus);
        bus.addListener(HexResearchClientForge::init);

        ForgeXPlatAPIImpl.init();
        HexResearchConfigForgeImpl.init();
        HexResearch.init();
        Events.init();
    }
}
