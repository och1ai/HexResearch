package name.dashkal.minecraft.hexresearch.fabric.cc;

import dev.onyxstudios.cca.api.v3.component.ComponentV3;
import name.dashkal.minecraft.hexresearch.HexResearch;
import net.minecraft.resources.ResourceLocation;

import java.util.SortedSet;

public interface CognitiveInducerMarkComponent extends ComponentV3 {
    ResourceLocation ID = HexResearch.id("ci_marks");

    /**
     * Marks the villager indicating that a cognitive inducer tried to take an impression.
     *
     * @param gameTime The current game time as per `Level.getGameTime()`
     */
    void mark(long gameTime);

    /** Returns a sorted set of all marks. */
    SortedSet<Long> getMarks();

    /**
     * Prunes any marks older than the configured retention time.
     *
     * @param gameTime The current game time as per `Level.getGameTime()`
     */
    void pruneMarks(long gameTime);
}
