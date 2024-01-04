package name.dashkal.minecraft.hexresearch.registry;

import name.dashkal.minecraft.hexresearch.advancements.trigger.AdvancementTrigger;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.advancements.CriterionTrigger;
import net.minecraft.advancements.CriterionTriggerInstance;

public class AdvancementTriggers {
    public static final AdvancementTrigger ADVANCEMENT_TRIGGER = register(new AdvancementTrigger());

    public static void init() {
        // Trigger static initialization
    }

    private static <T extends CriterionTrigger<U>, U extends CriterionTriggerInstance> T register(T trigger) {
        CriteriaTriggers.register(trigger);
        return trigger;
    }
}
