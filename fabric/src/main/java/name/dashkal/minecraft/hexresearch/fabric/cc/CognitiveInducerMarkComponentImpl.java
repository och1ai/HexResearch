package name.dashkal.minecraft.hexresearch.fabric.cc;

import name.dashkal.minecraft.hexresearch.block.entity.CognitiveInducerBlockEntity;
import net.minecraft.nbt.CompoundTag;

public class CognitiveInducerMarkComponentImpl implements CognitiveInducerMarkComponent {
    private long expirationTime = 0;

    @Override
    public long getExpirationTimeTicks() {
        return expirationTime;
    }

    @Override
    public void setMark(long expirationTimeTicks) {
        this.expirationTime = expirationTimeTicks;
    }

    @Override
    public void readFromNbt(CompoundTag tag) {
        this.expirationTime = tag.getLong(CognitiveInducerBlockEntity.TAG_VILLAGER_MARKED);
    }

    @Override
    public void writeToNbt(CompoundTag tag) {
        tag.putLong(CognitiveInducerBlockEntity.TAG_VILLAGER_MARKED, expirationTime);
    }
}
