package name.dashkal.minecraft.hexresearch.client.block.entity;

import name.dashkal.minecraft.hexresearch.block.entity.CognitiveInducerBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.level.Level;

public class CognitiveInducerClient {
    public static void handleParticlePacket(ResourceLocation dimensionId, int entityId, BlockPos blockPos) {
        // Find the matching entity
        Level level = Minecraft.getInstance().level;

        if (level != null && level.dimension().location().equals(dimensionId)) {
            Entity entity = Minecraft.getInstance().level.getEntity(entityId);
            if (entity instanceof Villager villager
                    && level.getBlockEntity(blockPos) instanceof CognitiveInducerBlockEntity cibe) {
                // Found it
                cibe.impressionParticles(level, villager, blockPos);
            }
        }
    }
}
