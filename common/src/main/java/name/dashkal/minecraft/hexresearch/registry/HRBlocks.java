package name.dashkal.minecraft.hexresearch.registry;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import name.dashkal.minecraft.hexresearch.HexResearch;
import name.dashkal.minecraft.hexresearch.block.CognitiveInducerBlock;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;

import java.util.function.Supplier;

public class HRBlocks {
    public static final DeferredRegister<Block> REGISTRY = DeferredRegister.create(HexResearch.MOD_ID, Registries.BLOCK);

    public static final RegistrySupplier<CognitiveInducerBlock> COGNITIVE_INDUCER = itemBlock(
            CognitiveInducerBlock.ID,
            () -> new CognitiveInducerBlock(BlockBehaviour.Properties.of().mapColor(MapColor.DEEPSLATE).sound(SoundType.DEEPSLATE).strength(2f, 4f))
    );

    public static void init() {
        System.out.println("[HexResearch] Starting HRBlocks.init()");
        try {
            System.out.println("[HexResearch] Registering blocks registry");
            REGISTRY.register();
            System.out.println("[HexResearch] HRBlocks registry registered successfully");
        } catch (Exception e) {
            System.err.println("[HexResearch] ERROR in HRBlocks.init(): " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    public static <T extends Block> RegistrySupplier<T> itemBlock(ResourceLocation id, Supplier<T> block) {
        return itemBlock(id, block, blockItemProperties());
    }

    public static <T extends Block> RegistrySupplier<T> itemBlock(ResourceLocation id, Supplier<T> block, Item.Properties itemProperties) {
        RegistrySupplier<T> blockSupplier = REGISTRY.register(id, block);
        HRItems.item(id, () -> new BlockItem(blockSupplier.get(), itemProperties));
        return blockSupplier;
    }

    private static Item.Properties blockItemProperties() {
        return new Item.Properties();
    }
}
