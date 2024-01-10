package name.dashkal.minecraft.hexresearch.xplat;

import name.dashkal.minecraft.hexresearch.effect.MindFatigueEffect;
import net.minecraft.world.entity.npc.Villager;

/**
 * Interface that houses platform-specific logic.
 * <p>
 * Most of the time this doesn't need to be used directly. Classes with platform specific logic will delegate though
 * this interface to the platform implementations in the forge and fabric modules.
 */
public abstract class XPlatAPI {
    // Initialized in the forge and fabric mod initializers
    protected static XPlatAPI instance = null;

    /** Returns an instance of this API appropriate for the current platform */
    public static XPlatAPI getInstance() { return instance; }

    /** Returns the Mind Fatigue potion effect. */
    public abstract MindFatigueEffect getMindFatigueEffect();

    /** Marks the villager with the world time for reading later. */
    public abstract void cognitiveInducerMarkVillager(Villager villager, long expirationTime);

    /** Returns true if the villager is marked and the time has not expired. */
    public abstract boolean cognitiveInducerIsVillagerMarked(Villager villager, long worldTime);
}
