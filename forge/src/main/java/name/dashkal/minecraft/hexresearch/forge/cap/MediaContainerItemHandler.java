package name.dashkal.minecraft.hexresearch.forge.cap;

import name.dashkal.minecraft.hexresearch.block.entity.AbstractMediaContainerBlockEntity;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MediaContainerItemHandler implements IItemHandler {
    private final AbstractMediaContainerBlockEntity blockEntity;

    public MediaContainerItemHandler(AbstractMediaContainerBlockEntity blockEntity) {
        this.blockEntity = blockEntity;
    }

    @Override
    public int getSlots() {
        return blockEntity.getContainerSize();
    }

    @Override
    public @NotNull ItemStack getStackInSlot(int slotId) {
        return blockEntity.getItem(slotId);
    }

    @Override
    public @NotNull ItemStack insertItem(int slotId, @NotNull ItemStack itemStack, boolean simulate) {
        if (!isItemValid(slotId, itemStack)) {
            return itemStack;
        }

        ItemStack stack = itemStack.copy();

        if (!simulate) {
            blockEntity.insertMedia(stack, false);
        } else {
            // We're simulating, but the capability contract still wants us to return an ItemStack with the media taken.
            blockEntity.extractMediaFromItem(stack, false);
        }

        return stack;
    }

    @Override
    public @NotNull ItemStack extractItem(int slotId, int amount, boolean simulate) {
        return ItemStack.EMPTY;
    }

    @Override
    public int getSlotLimit(int slotId) {
        return 64;
    }

    @Override
    public boolean isItemValid(int slotId, @NotNull ItemStack itemStack) {
        return blockEntity.canPlaceItem(slotId, itemStack);
    }

    public static class CapabilityProvider implements ICapabilityProvider {
        private final MediaContainerItemHandler handler;

        public CapabilityProvider(AbstractMediaContainerBlockEntity blockEntity) {
            this.handler = new MediaContainerItemHandler(blockEntity);
        }

        @Override
        public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> capability, @Nullable Direction arg) {
            return ForgeCapabilities.ITEM_HANDLER.orEmpty(capability, LazyOptional.of(() -> handler));
        }
    }
}
