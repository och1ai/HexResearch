package name.dashkal.minecraft.hexresearch.registry;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import name.dashkal.minecraft.hexresearch.HexResearch;
import name.dashkal.minecraft.hexresearch.block.CognitiveInducerBlock;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.util.function.Supplier;

public class HRBlocks {
    public static final DeferredRegister<Block> REGISTRY = DeferredRegister.create(HexResearch.MOD_ID, Registry.BLOCK_REGISTRY);

    public static final RegistrySupplier<CognitiveInducerBlock> ARTIFICIAL_MIND = itemBlock(CognitiveInducerBlock.ID, CognitiveInducerBlock::getInstance);

    public static void init() {
        REGISTRY.register();
    }

    public static <T extends Block> RegistrySupplier<T> itemBlock(ResourceLocation id, Supplier<T> block) {
        return itemBlock(id, block, new Item.Properties().tab(HRItems.HEX_RESEARCH_TAB));
    }

    public static <T extends Block> RegistrySupplier<T> itemBlock(ResourceLocation id, Supplier<T> block, Item.Properties properties) {
        RegistrySupplier<T> blockSupplier = REGISTRY.register(id, block);
        HRItems.item(id, () -> new BlockItem(blockSupplier.get(), properties));
        return blockSupplier;
    }

    public static <T extends Block> T noItemBlock(ResourceLocation id, Supplier<T> block) {
        REGISTRY.register(id, block);
        return block.get();
    }
}
