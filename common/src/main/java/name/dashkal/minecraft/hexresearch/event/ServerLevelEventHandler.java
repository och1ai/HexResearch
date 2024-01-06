package name.dashkal.minecraft.hexresearch.event;

import dev.architectury.event.events.common.LifecycleEvent;
import name.dashkal.minecraft.hexresearch.util.PatternFixer;
import net.minecraft.server.level.ServerLevel;

public class ServerLevelEventHandler {
    public static void init() {
        LifecycleEvent.SERVER_LEVEL_LOAD.register(ServerLevelEventHandler::onServerLevelLoad);
    }

    public static void onServerLevelLoad(ServerLevel serverLevel) {
        PatternFixer.ensurePerWorldPatterns(serverLevel);
    }
}
