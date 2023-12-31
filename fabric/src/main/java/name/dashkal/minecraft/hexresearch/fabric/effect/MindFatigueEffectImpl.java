package name.dashkal.minecraft.hexresearch.fabric.effect;

import name.dashkal.minecraft.hexresearch.effect.MindFatigueEffect;

/**
 * Marker potion effect used to indicate "mental fatigue".  Attempting to Thought Sieve a villager who has this
 * effect active will cause additional mind harm.
 */
public class MindFatigueEffectImpl extends MindFatigueEffect {
    public static final MindFatigueEffectImpl INSTANCE = new MindFatigueEffectImpl();

    public MindFatigueEffectImpl() {
        super();
    }
}
