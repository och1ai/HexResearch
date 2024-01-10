package name.dashkal.minecraft.hexresearch.network;

import at.petrak.hexcasting.common.entities.EntityWallScroll;
import dev.architectury.networking.NetworkManager;
import io.netty.buffer.Unpooled;
import name.dashkal.minecraft.hexresearch.HexResearch;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;

public class ScrollSyncPacket {
    public static final ResourceLocation PACKET_ID = new ResourceLocation(HexResearch.MOD_ID, "scroll_sync");

    private final int entityId;
    private final ItemStack newScroll;

    private final FriendlyByteBuf buf;

    public ScrollSyncPacket(int entityId, ItemStack newScroll) {
        this.entityId = entityId;
        this.newScroll = newScroll;
        this.buf = new FriendlyByteBuf(Unpooled.buffer());
        this.buf.writeInt(entityId);
        this.buf.writeItem(newScroll);
    }

    public int getEntityId() {
        return entityId;
    }

    public ItemStack getNewScroll() {
        return newScroll;
    }

    public void sendToPlayer(ServerPlayer player) {
        NetworkManager.sendToPlayer(player, PACKET_ID, buf);
    }

    public static void s2cHandler(FriendlyByteBuf buf, NetworkManager.PacketContext context) {
        int entityId = buf.readInt();
        ItemStack scroll = buf.readItem();

        context.queue(() -> {
            // Find the matching entity
            Entity entity = Minecraft.getInstance().level.getEntity(entityId);
            if (entity != null) {
                // Found it
                if (entity instanceof EntityWallScroll wallScroll) {
                    // It's a wall scroll
                    wallScroll.scroll = scroll;
                    wallScroll.recalculateDisplay();
                }
            }
        });
    }
}
