package name.dashkal.minecraft.hexresearch.fabric.cc;

import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistryV3;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentInitializer;
import name.dashkal.minecraft.hexresearch.HexResearch;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.npc.Villager;

public class HexResearchCC implements EntityComponentInitializer {
    public static final ComponentKey<CognitiveInducerMarkComponent> COGNITIVE_INDUCER_MARK =
            ComponentRegistryV3.INSTANCE.getOrCreate(new ResourceLocation(HexResearch.MOD_ID, "ci_mark"), CognitiveInducerMarkComponent.class);

    @Override
    public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
        registry.registerFor(Villager.class, COGNITIVE_INDUCER_MARK, v -> new CognitiveInducerMarkComponentImpl());
    }
}
