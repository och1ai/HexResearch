package name.dashkal.minecraft.hexresearch.fabric.event;

import dev.architectury.event.events.common.PlayerEvent;
import name.dashkal.minecraft.hexresearch.HexResearch;
import name.dashkal.minecraft.hexresearch.fabric.network.NetworkingFabric;
import net.minecraft.server.level.ServerPlayer;

public class PlayerEventHandler {
    public static void init() {
        PlayerEvent.PLAYER_JOIN.register(PlayerEventHandler::onPlayerConnected);
    }

    public static void onPlayerConnected(ServerPlayer player) {
        NetworkingFabric.sendServerConfig(player, HexResearch.getServerConfig());
    }
}
