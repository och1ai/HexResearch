package name.dashkal.minecraft.hexresearch.forge.cap;

import name.dashkal.minecraft.hexresearch.HexResearch;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.npc.Villager;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.util.NonNullConsumer;
import net.minecraftforge.common.util.NonNullFunction;

import java.util.SortedSet;

/** Capability to be attached to Villagers marked by the Cognitive Inducer */
public interface ICognitiveInducerMarks {
    Capability<ICognitiveInducerMarks> CAPABILITY = CapabilityManager.get(new CapabilityToken<>() {});
    ResourceLocation ID = HexResearch.id("ci_marks");

    /**
     * Passes the {@link ICognitiveInducerMarks} capability for the given {@link Villager} to the consumer.
     * <p>
     * Note: In practice, the capability will always be present as it is attached unconditionally in {@link name.dashkal.minecraft.hexresearch.forge.event.CapabilityEventHandler}.
     */
    static void with(Villager villager, NonNullConsumer<ICognitiveInducerMarks> consumer) {
        villager.getCapability(CAPABILITY).ifPresent(consumer);
    }

    /**
     * Calls func with the {@link ICognitiveInducerMarks} capability for the given {@link Villager}.
     * <p>
     * Note: In practice, the capability will always be present as it is attached unconditionally in {@link name.dashkal.minecraft.hexresearch.forge.event.CapabilityEventHandler}.
     */
    static <T> T withF(Villager villager, NonNullFunction<ICognitiveInducerMarks, T> func) {
        return villager.getCapability(CAPABILITY).map(func).orElse(null);
    }

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
