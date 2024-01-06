package name.dashkal.minecraft.hexresearch.client.scrying;

import at.petrak.hexcasting.api.client.ScryingLensOverlayRegistry;
import com.mojang.datafixers.util.Pair;
import name.dashkal.minecraft.hexresearch.block.entity.CognitiveInducerBlockEntity;
import name.dashkal.minecraft.hexresearch.registry.HRBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

public class CognitiveInducerOverlay implements ScryingLensOverlayRegistry.OverlayBuilder {
    @Override
    public void addLines(List<Pair<ItemStack, Component>> lines, BlockState state, BlockPos pos, Player observer, Level world, Direction hitFace) {
        BlockEntity be = world.getBlockEntity(pos);
        if (be instanceof CognitiveInducerBlockEntity inducer) {
            ItemStack ciStack = new ItemStack(HRBlocks.ARTIFICIAL_MIND.get());

            inducer.getImpressedMind().ifPresentOrElse(
                    mind -> { lines.add(Pair.of(ciStack, mind.toComponent())); },
                    ()   -> { lines.add(Pair.of(ciStack, Component.translatable("text.hexresearch.cognitive_inducer.empty"))); }
            );
        }
    }
}
