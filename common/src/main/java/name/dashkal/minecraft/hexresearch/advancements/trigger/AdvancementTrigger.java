package name.dashkal.minecraft.hexresearch.advancements.trigger;

import com.google.gson.JsonObject;
import name.dashkal.minecraft.hexresearch.HexResearch;
import net.minecraft.advancements.critereon.AbstractCriterionTriggerInstance;
import net.minecraft.advancements.critereon.DeserializationContext;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.GsonHelper;

import javax.annotation.Nonnull;

/** Advancement trigger that fires when an advancement is gained */
public class AdvancementTrigger extends SimpleCriterionTrigger<AdvancementTrigger.Instance> {
    public static final ResourceLocation ID = new ResourceLocation(HexResearch.MOD_ID, "advancement");

    public static final String TAG_ADVANCEMENT_ID = "advancement";

    @Override @Nonnull
    public ResourceLocation getId() {
        return ID;
    }

    @Override @Nonnull
    protected Instance createInstance(@Nonnull JsonObject jsonObject, @Nonnull EntityPredicate.Composite predicate, @Nonnull DeserializationContext deserializationContext) {
        ResourceLocation advancement = new ResourceLocation(GsonHelper.getAsString(jsonObject, TAG_ADVANCEMENT_ID));
        return new Instance(predicate, advancement);
    }

    public void trigger(ServerPlayer player, ResourceLocation advancement) {
        HexResearch.LOGGER.debug("AdvancementTrigger.trigger(" + player.getName().getString() + ", " + advancement + ")");
        super.trigger(player, inst -> inst.test(advancement));
    }

    public static class Instance extends AbstractCriterionTriggerInstance {
        private final ResourceLocation advancement;

        public Instance(EntityPredicate.Composite predicate, ResourceLocation advancement) {
            super(AdvancementTrigger.ID, predicate);

            if (advancement == null) {
                throw new IllegalArgumentException("AdvancementTrigger "+ TAG_ADVANCEMENT_ID + " must not resolve to null");
            }
            this.advancement = advancement;
        }

        private boolean test(ResourceLocation advancement) {
            HexResearch.LOGGER.debug("AdvancementTrigger.Instance(" + this.advancement + ").test(" + advancement + ")");
            return this.advancement.equals(advancement);
        }
    }
}
