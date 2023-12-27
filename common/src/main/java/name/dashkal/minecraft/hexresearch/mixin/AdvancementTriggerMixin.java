package name.dashkal.minecraft.hexresearch.mixin;

import name.dashkal.minecraft.hexresearch.registry.HexResearchAdvancementTriggerRegistry;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.server.PlayerAdvancements;
import net.minecraft.server.level.ServerPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Mixin used to hook into advancement grants, enabling the "hexresearch:advancement" advancement trigger.
 */
@Mixin(PlayerAdvancements.class)
public abstract class AdvancementTriggerMixin {
    @Inject(at = @At("RETURN"), method = "award(Lnet/minecraft/advancements/Advancement;Ljava/lang/String;)Z")
    private void award(Advancement advancement, String string, CallbackInfoReturnable<Boolean> cir) {
        if (getOrStartProgress(advancement).isDone()) {
            HexResearchAdvancementTriggerRegistry.ADVANCEMENT_TRIGGER.trigger(player, advancement.getId());
        }
    }

    @Shadow
    private ServerPlayer player;

    @Shadow
    public abstract AdvancementProgress getOrStartProgress(Advancement advancement);
}
