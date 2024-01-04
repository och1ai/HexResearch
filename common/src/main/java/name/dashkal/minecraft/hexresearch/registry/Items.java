package name.dashkal.minecraft.hexresearch.registry;

import dev.architectury.registry.CreativeTabRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import name.dashkal.minecraft.hexresearch.HexResearch;
import name.dashkal.minecraft.hexresearch.block.ArtificialMindBlock;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;

import java.util.function.Supplier;

public class Items {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(HexResearch.MOD_ID, Registry.ITEM_REGISTRY);

    public static final CreativeModeTab HEX_RESEARCH_TAB = CreativeTabRegistry.create(HexResearch.id("hex_research"), () -> new ItemStack(net.minecraft.world.item.Items.AMETHYST_SHARD));

    // Items
//    public static final RegistrySupplier<Item> DUMMY_ITEM = ITEMS.register("dummy_item", () -> new ItemDummy(new Item.Properties().tab(HEX_RESEARCH_TAB)));

    public static void init() {
        ITEMS.register();
    }

    public static <T extends Item> RegistrySupplier<T> item(ResourceLocation id, Supplier<T> item) {
        return ITEMS.register(id, item);
    }
}
