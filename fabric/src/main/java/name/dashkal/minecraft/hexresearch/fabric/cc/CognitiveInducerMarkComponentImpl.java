package name.dashkal.minecraft.hexresearch.fabric.cc;

import name.dashkal.minecraft.hexresearch.HexResearch;
import name.dashkal.minecraft.hexresearch.block.entity.CognitiveInducerBlockEntity;
import net.minecraft.nbt.CompoundTag;

import java.util.Arrays;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class CognitiveInducerMarkComponentImpl implements CognitiveInducerMarkComponent {
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

    @Override
    public void readFromNbt(CompoundTag tag) {
        long[] rawMarks = tag.getLongArray(TAG_MARKS);
        marks = Arrays.stream(rawMarks).boxed().collect(Collectors.toCollection(TreeSet::new));
    }

    @Override
    public void writeToNbt(CompoundTag tag) {
        tag.putLongArray(TAG_MARKS, marks.stream().toList());
    }
}
