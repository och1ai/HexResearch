package name.dashkal.minecraft.hexresearch.fabric.event;

import dev.architectury.event.events.common.LifecycleEvent;
import name.dashkal.minecraft.hexresearch.fabric.config.HexResearchConfigFabricImpl;
import net.minecraft.server.MinecraftServer;

public class LifecycleEventHandler {
    public static void init() {
        LifecycleEvent.SERVER_STARTING.register(LifecycleEventHandler::onServerStartup);
    }

    public static void onServerStartup(MinecraftServer server) {
        HexResearchConfigFabricImpl.getInstance().loadServerConfig();
    }
}
