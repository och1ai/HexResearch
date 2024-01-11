package name.dashkal.minecraft.hexresearch.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Abstract class to handle logic relating to having a block entity
 * @param <T> the type of the block entity
 */
public class AbstractBlockEntityBlock<T extends BlockEntity> extends Block implements EntityBlock {
    @Nonnull
    private final Class<T> blockEntityClass;
    @Nonnull
    private final BiFunction<BlockPos, BlockState, T> newBlockEntity;

    /**
     * @param properties block properties to pass to `Block`'s constructor
     * @param blockEntityClass the class of the block entity this block uses
     * @param newBlockEntity a constructor for the block entity this block uses
     */
    public AbstractBlockEntityBlock(Properties properties, @Nonnull Class<T> blockEntityClass, @Nonnull BiFunction<BlockPos, BlockState, T> newBlockEntity) {
        super(properties);
        this.blockEntityClass = blockEntityClass;
        this.newBlockEntity = newBlockEntity;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@Nonnull BlockPos blockPos, @Nonnull BlockState blockState) {
        return newBlockEntity.apply(blockPos, blockState);
    }

    /**
     * Hand the consumer the block entity if it is present and of the expected class at the given coordinates.
     *
     * @param blockGetter the level to find the block entity in
     * @param blockPos the position to find the block entity at
     * @param consumer a consumer that will be handed the block entity
     */
    protected void withBlockEntity(@Nonnull BlockGetter blockGetter, @Nonnull BlockPos blockPos, @Nonnull Consumer<T> consumer) {
        BlockEntity blockEntity = blockGetter.getBlockEntity(blockPos);
        if (blockEntity != null && blockEntityClass.isAssignableFrom(blockEntity.getClass())) {
            consumer.accept(blockEntityClass.cast(blockEntity));
        }
    }

    /**
     * Passes the block entity to the given function if it is present and of the expected class.
     *
     * @param blockGetter the level to find the block entity in
     * @param blockPos the position to find the block entity at
     * @param function a function that accepts the block entity
     * @return an `Optional` containing the return value of the function or empty if the block entity was not present
     */
    protected <A> Optional<A> withBlockEntityF(@Nonnull BlockGetter blockGetter, @Nonnull BlockPos blockPos, @Nonnull Function<T, A> function) {
        BlockEntity blockEntity = blockGetter.getBlockEntity(blockPos);
        if (blockEntity != null && blockEntityClass.isAssignableFrom(blockEntity.getClass())) {
            return Optional.of(function.apply(blockEntityClass.cast(blockEntity)));
        } else {
            return Optional.empty();
        }
    }
}
