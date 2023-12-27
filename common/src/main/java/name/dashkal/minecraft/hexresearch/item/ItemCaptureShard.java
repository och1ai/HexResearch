package name.dashkal.minecraft.hexresearch.item;

import dev.architectury.platform.Platform;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nonnull;
import java.util.UUID;

public class ItemCaptureShard extends Item {
    public static String TAG_CAPTURED_ENTITY = "entity";

    public static String EMPTY_DESC_ID = "item.hexresearch.capture_shard.empty";
    public static String FILLED_DESC_ID = "item.hexresearch.capture_shard.filled";

    public ItemCaptureShard(Properties properties) {
        super(properties.stacksTo(1));
    }

    @Override
    public @Nonnull String getDescriptionId(@Nonnull ItemStack itemStack) {
        if (isFilled(itemStack)) {
            return FILLED_DESC_ID;
        } else {
            return EMPTY_DESC_ID;
        }
    }

    public boolean isFilled(ItemStack itemStack) {
        if (this.equals(itemStack.getItem())) {
            return itemStack.getTagElement(TAG_CAPTURED_ENTITY) != null;
        }

        return false;
    }

    @Override @Nonnull
    public InteractionResult useOn(UseOnContext useOnContext) {
        if (!useOnContext.getLevel().isClientSide) {
            if (isFilled(useOnContext.getItemInHand())) {
                release(
                        useOnContext.getItemInHand(),
                        (ServerLevel) useOnContext.getLevel(),
                        useOnContext.getClickLocation(),
                        useOnContext.getPlayer().isCreative()
                );
            }
        }
        return InteractionResult.CONSUME;
    }

    public boolean capture(ItemStack itemStack, LivingEntity livingEntity) {
        if (!isFilled(itemStack)) {
            CompoundTag tag = new CompoundTag();
            livingEntity.save(tag);
            itemStack.addTagElement(TAG_CAPTURED_ENTITY, tag);
            livingEntity.remove(Entity.RemovalReason.CHANGED_DIMENSION);

            return true;
        }

        return false;
    }

    public boolean release(ItemStack itemStack, ServerLevel world, Vec3 position) {
        return release(itemStack, world, position, false);
    }

    public boolean release(ItemStack itemStack, ServerLevel world, Vec3 position, boolean keepCapture) {
        if (isFilled(itemStack)) {
            CompoundTag tag = itemStack.getTagElement(TAG_CAPTURED_ENTITY);
            Entity entity = EntityType.loadEntityRecursive(tag, world, e -> {
                e.moveTo(position.x, position.y, position.z, e.getYRot(), e.getXRot());
                return e;
            });
            if (!keepCapture) {
                itemStack.removeTagKey(TAG_CAPTURED_ENTITY);
            }
            if (entity == null) {
                // Failed to load an entity from the shard
                return false;
            }
            if (world.getEntity(entity.getUUID()) != null) {
                // We're duplicating; assign a fresh ID.
                entity.setUUID(UUID.randomUUID());
            }
            return world.tryAddFreshEntityWithPassengers(entity);
        }
        return false;
    }
}
