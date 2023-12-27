package name.dashkal.minecraft.hexresearch.effect;

import name.dashkal.minecraft.hexresearch.HexResearch;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Marker potion effect used to indicate "mental fatigue".  Attempting to Thought Sieve a villager who has this
 * effect active will cause additional mind harm.
 */
public class MindFatigueEffect extends MobEffect {
    public static final MindFatigueEffect INSTANCE = new MindFatigueEffect();

    public static final ResourceLocation ID = new ResourceLocation(HexResearch.MOD_ID, "mind_fatigue");

    public MindFatigueEffect() {
        super(MobEffectCategory.HARMFUL, 0x404040);
    }

    public static MobEffectInstance effectInstance(int durationTicks) {
        return new MobEffectInstance(INSTANCE, durationTicks);
    }

    @Override
    public void applyEffectTick(@Nonnull LivingEntity livingEntity, int i) {}

    @Override
    public boolean isDurationEffectTick(int i, int j) {
        return false;
    }

    @Override
    public void applyInstantenousEffect(@Nullable Entity entity, @Nullable Entity entity2, @Nonnull LivingEntity livingEntity, int i, double d) {}
}
