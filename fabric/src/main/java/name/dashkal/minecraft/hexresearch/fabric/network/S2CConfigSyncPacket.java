package name.dashkal.minecraft.hexresearch.fabric.network;

import at.petrak.hexcasting.common.entities.EntityWallScroll;
import com.google.gson.Gson;
import dev.architectury.networking.NetworkManager;
import io.netty.buffer.Unpooled;
import name.dashkal.minecraft.hexresearch.HexResearch;
import name.dashkal.minecraft.hexresearch.config.ServerConfig;
import name.dashkal.minecraft.hexresearch.fabric.config.HexResearchConfigFabricImpl;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import org.apache.logging.log4j.core.jmx.Server;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class S2CConfigSyncPacket {
    public static final ResourceLocation PACKET_ID = new ResourceLocation(HexResearch.MOD_ID, "server_config");

    private final ServerConfig serverConfig;

    private final FriendlyByteBuf buf;

    public S2CConfigSyncPacket(ServerConfig serverConfig) {
        this.serverConfig = serverConfig;
        Gson gson = new Gson();
        this.buf = new FriendlyByteBuf(Unpooled.buffer());
        this.buf.writeUtf(gson.toJson(serverConfig, ServerConfig.class));
    }

    public ServerConfig getServerConfig() {
        return serverConfig;
    }

    public void sendToPlayer(ServerPlayer player) {
        NetworkManager.sendToPlayer(player, PACKET_ID, buf);
    }

    public static void s2cHandler(FriendlyByteBuf buf, NetworkManager.PacketContext context) {
        String rawConfig = buf.readUtf();
        Gson gson = new Gson();
        ServerConfig serverConfig = gson.fromJson(rawConfig, ServerConfig.class);
        HexResearchConfigFabricImpl.getInstance().setServerConfig(serverConfig);
    }
}
