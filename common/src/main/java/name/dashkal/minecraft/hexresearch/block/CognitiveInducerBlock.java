package name.dashkal.minecraft.hexresearch.block;

import at.petrak.hexcasting.api.utils.MediaHelper;
import name.dashkal.minecraft.hexresearch.HexResearch;
import name.dashkal.minecraft.hexresearch.block.entity.AbstractMediaContainerBlockEntity;
import name.dashkal.minecraft.hexresearch.block.entity.CognitiveInducerBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class CognitiveInducerBlock extends Block implements EntityBlock {
    public static final ResourceLocation ID = HexResearch.id("cognitive_inducer");

    // Exported from BlockBench / Mod Utils
    public static final VoxelShape SHAPE = Shapes.or(
            Block.box(0, 0, 0, 16, 2, 2),
            Block.box(0, 0, 14, 16, 2, 16),
            Block.box(0, 0, 2, 2, 2, 14),
            Block.box(14, 0, 2, 16, 2, 14),
            Block.box(14, 14, 2, 16, 16, 14),
            Block.box(0, 14, 2, 2, 16, 14),
            Block.box(0, 14, 14, 16, 16, 16),
            Block.box(0, 14, 0, 16, 16, 2),
            Block.box(14, 2, 14, 16, 14, 16),
            Block.box(0, 2, 14, 2, 14, 16),
            Block.box(14, 2, 0, 16, 14, 2),
            Block.box(0, 2, 0, 2, 14, 2)
    );

    private static CognitiveInducerBlock instance;

    public CognitiveInducerBlock(Properties properties) {
        super(properties);
    }

    public static CognitiveInducerBlock getInstance() {
        synchronized (CognitiveInducerBlock.class) {
            if (instance == null) {
                instance = new CognitiveInducerBlock(
                        Properties.of(Material.STONE, MaterialColor.DEEPSLATE)
                                .sound(SoundType.DEEPSLATE)
                                .strength(2f, 4f)
                );
            }
            return instance;
        }
    }

    @SuppressWarnings("deprecation")
    @Nonnull
    @Override
    public InteractionResult use(@Nonnull BlockState blockState, @Nonnull Level level, @Nonnull BlockPos blockPos, @Nonnull Player player, @Nonnull InteractionHand interactionHand, @Nonnull BlockHitResult blockHitResult) {
        if (level.getBlockEntity(blockPos) instanceof AbstractMediaContainerBlockEntity mcbe) {
            ItemStack item = player.getItemInHand(interactionHand);
            if (MediaHelper.isMediaItem(item)) {
                return mcbe.insertMedia(item, false) ? InteractionResult.SUCCESS : InteractionResult.CONSUME;
            } else {
                return InteractionResult.PASS;
            }
        } else {
            return InteractionResult.PASS;
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    public void randomTick(@Nonnull BlockState blockState, @Nonnull ServerLevel serverLevel, @Nonnull BlockPos blockPos, @Nonnull RandomSource randomSource) {
        if (serverLevel.getBlockEntity(blockPos) instanceof CognitiveInducerBlockEntity be) {
            be.randomTick(blockState, serverLevel, blockPos, randomSource);
        }
    }

    @Override
    public boolean isRandomlyTicking(@Nonnull BlockState blockState) {
        return true;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@Nonnull Level level, @Nonnull BlockState blockState, @Nonnull BlockEntityType<T> blockEntityType) {
        if (level.isClientSide) {
            return (_l, _p, _s, _be) -> {
                if (_be instanceof CognitiveInducerBlockEntity ambe) {
                    ambe.tick(_l, _p);
                }
            };
        } else {
            return null;
        }
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@Nonnull BlockPos blockPos, @Nonnull BlockState blockState) {
        return new CognitiveInducerBlockEntity(blockPos, blockState);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(@Nonnull BlockPlaceContext blockPlaceContext) {
        return defaultBlockState();
    }

    @SuppressWarnings("deprecation")
    @Nonnull
    @Override
    public VoxelShape getShape(@Nonnull BlockState blockState, @Nonnull BlockGetter blockGetter, @Nonnull BlockPos blockPos, @Nonnull CollisionContext collisionContext) {
        return Shapes.block();
    }

    @SuppressWarnings("deprecation")
    @Nonnull
    @Override
    public VoxelShape getOcclusionShape(@Nonnull BlockState blockState, @Nonnull BlockGetter blockGetter, @Nonnull BlockPos blockPos) {
        return SHAPE;
    }

    @SuppressWarnings("deprecation")
    @Override
    public int getLightBlock(@Nonnull BlockState blockState, BlockGetter blockGetter, @Nonnull BlockPos blockPos) {
        if (blockGetter.getBlockEntity(blockPos) instanceof CognitiveInducerBlockEntity be) {
            return be.getImpressedMind().map(m -> m.rank() * 5).orElse(0);
        }
        return 0;
    }
}
