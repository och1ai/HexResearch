package name.dashkal.minecraft.hexresearch.fabric.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import name.dashkal.minecraft.hexresearch.effect.MindFatigueEffect;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;

/**
 * Mixin used to keep milk from curing mind fatigue
 */
@Mixin(LivingEntity.class)
public abstract class MindFatigueMixin extends Entity {
    public MindFatigueMixin(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }

    @Shadow @Final
    private Map<MobEffect, MobEffectInstance> activeEffects;

    @Inject(at = @At("HEAD"), method = "removeAllEffects()Z")
    private void removeAllEffectsHead(CallbackInfoReturnable<Boolean> cir, @Share("effectInstance") LocalRef<MobEffectInstance> effectInstance) {
        MobEffect mindFatigueEffect = MindFatigueEffect.getInstance();
        if (activeEffects.containsKey(mindFatigueEffect)) {
            effectInstance.set(activeEffects.remove(mindFatigueEffect));
        }
    }

    @Inject(at = @At("RETURN"), method = "removeAllEffects()Z")
    private void removeAllEffectsReturn(CallbackInfoReturnable<Boolean> cir, @Share("effectInstance") LocalRef<MobEffectInstance> effectInstance) {
        if (effectInstance.get() != null) {
            activeEffects.put(MindFatigueEffect.getInstance(), effectInstance.get());
            effectInstance.set(null);
        }
    }
}
