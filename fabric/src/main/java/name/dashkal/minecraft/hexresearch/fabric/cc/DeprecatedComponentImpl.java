package name.dashkal.minecraft.hexresearch.fabric.cc;

import net.minecraft.nbt.CompoundTag;

import javax.annotation.Nonnull;

public class DeprecatedComponentImpl implements DeprecatedComponent {

    public <T> DeprecatedComponentImpl(T t) {}
    @Override
    public void readFromNbt(@Nonnull CompoundTag tag) {}

    @Override
    public void writeToNbt(@Nonnull CompoundTag tag) {}
}
