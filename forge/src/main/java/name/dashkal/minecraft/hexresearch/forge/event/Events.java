package name.dashkal.minecraft.hexresearch.forge.event;

import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.MinecraftForge;

public class Events {
    public static void init() {
        // Capabilities
        MinecraftForge.EVENT_BUS.addGenericListener(BlockEntity.class, CapabilityEventHandler::onAttachCapabilitiesBlockEntity);
    }
}
