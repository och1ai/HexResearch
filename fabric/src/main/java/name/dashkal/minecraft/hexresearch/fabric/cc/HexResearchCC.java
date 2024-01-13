package name.dashkal.minecraft.hexresearch.fabric.cc;

import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistryV3;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentInitializer;
import name.dashkal.minecraft.hexresearch.HexResearch;
import net.minecraft.world.entity.npc.Villager;

public class HexResearchCC implements EntityComponentInitializer {
    public static final ComponentKey<CognitiveInducerMarkComponent> COGNITIVE_INDUCER_MARK =
            ComponentRegistryV3.INSTANCE.getOrCreate(CognitiveInducerMarkComponent.ID, CognitiveInducerMarkComponent.class);

    public static final ComponentKey<DeprecatedComponent> DEPRECATED_CI_MARK =
            ComponentRegistryV3.INSTANCE.getOrCreate(HexResearch.id("ci_mark"), DeprecatedComponent.class);

    @Override
    public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
        // Deprecated "ci_mark"
        registry.registerFor(Villager.class, DEPRECATED_CI_MARK, DeprecatedComponentImpl::new);

        // Cognitive Inducer Mark History
        registry.registerFor(Villager.class, COGNITIVE_INDUCER_MARK, v -> new CognitiveInducerMarkComponentImpl());
    }
}
