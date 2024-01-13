package name.dashkal.minecraft.hexresearch.forge.cap;

import name.dashkal.minecraft.hexresearch.HexResearch;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

/** Implementation of the CognitiveInducerMarks capability */
public class CognitiveInducerMarksImpl implements ICognitiveInducerMarks {
    private static final String TAG_MARKS = "marks";
    private SortedSet<Long> marks = new TreeSet<>();

    @Override
    public void mark(long gameTime) {
        marks.add(gameTime);
    }

    @Override
    public SortedSet<Long> getMarks() {
        return marks;
    }

    @Override
    public void pruneMarks(long gameTime) {
        long expirationTimeSeconds = HexResearch.getServerConfig().mindTrainingConfig().impressionMarkExpirationTimeSeconds();
        long expiration = gameTime - (expirationTimeSeconds * 20L);
        while (!marks.isEmpty() && (marks.first() <= expiration)) {
            marks.remove(marks.first());
        }
    }

    private void deserializeNBT(CompoundTag tag) {
        long[] rawMarks = tag.getLongArray(TAG_MARKS);
        marks = Arrays.stream(rawMarks).boxed().collect(Collectors.toCollection(TreeSet::new));
    }

    private CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putLongArray(TAG_MARKS, new ArrayList<>(marks));
        return tag;
    }

    public static class CIMCapabilityProvider implements ICapabilitySerializable<CompoundTag> {
        private final LazyOptional<CognitiveInducerMarksImpl> supplier = LazyOptional.of(CognitiveInducerMarksImpl::new);

        @Override
        public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> capability, @Nullable Direction arg) {
            return ICognitiveInducerMarks.CAPABILITY.orEmpty(capability, supplier.cast());
        }

        @Override
        public CompoundTag serializeNBT() {
            return supplier.<CognitiveInducerMarksImpl>cast().map(CognitiveInducerMarksImpl::serializeNBT).orElse(new CompoundTag());
        }

        @Override
        public void deserializeNBT(CompoundTag tag) {
            supplier.<CognitiveInducerMarksImpl>cast().ifPresent(c -> c.deserializeNBT(tag));
        }
    }
}
