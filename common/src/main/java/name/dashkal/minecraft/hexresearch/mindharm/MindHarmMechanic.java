package name.dashkal.minecraft.hexresearch.mindharm;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.npc.Villager;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/** Interface describing a mechanic used to harm a villager's mind when spells like Thought Sieve go wrong. */
public interface MindHarmMechanic {
    /** Unique identifier of this mind harm mechanic. */
    @Nonnull ResourceLocation getId();
    /** Severity of this mechanic. Used to prioritize selection when a villager is harmed. */
    @Nonnull MindHarmSeverity getSeverity();
    /** Applies the mechanic to the given villager. Returns <code>true</code> if it was successfully applied. */
    Boolean doHarm(@Nullable Entity attacker, @Nonnull Villager villager);
}
