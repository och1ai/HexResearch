package name.dashkal.minecraft.hexresearch.client.scrying;

import at.petrak.hexcasting.api.client.ScryingLensOverlayRegistry;
import name.dashkal.minecraft.hexresearch.block.CognitiveInducerBlock;
import name.dashkal.minecraft.hexresearch.block.entity.AbstractMediaContainerBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class ScryingLensOverlays {
    public static void init() {
        // Cognitive Inducer
        ScryingLensOverlayRegistry.addDisplayer(CognitiveInducerBlock.ID, new CognitiveInducerOverlay());

        // Any AbstractMediaContainerBlockEntity
        // Keep this at or near the bottom
        ScryingLensOverlayRegistry.addPredicateDisplayer(
                (BlockState state, BlockPos pos, Player observer, Level world, Direction hitFace) ->
                    (world.getBlockEntity(pos) instanceof AbstractMediaContainerBlockEntity)
            , new MediaContainerOverlay());
    }
}
