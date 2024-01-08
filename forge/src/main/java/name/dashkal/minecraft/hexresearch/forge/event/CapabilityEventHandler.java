package name.dashkal.minecraft.hexresearch.forge.event;

import name.dashkal.minecraft.hexresearch.HexResearch;
import name.dashkal.minecraft.hexresearch.block.entity.AbstractMediaContainerBlockEntity;
import name.dashkal.minecraft.hexresearch.forge.cap.MediaContainerItemHandler;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class CapabilityEventHandler {
    @SubscribeEvent
    public static void onAttachCapabilitiesBlockEntity(AttachCapabilitiesEvent<BlockEntity> event) {
        if (event.getObject() instanceof AbstractMediaContainerBlockEntity be) {
            event.addCapability(new ResourceLocation(HexResearch.MOD_ID, "mediacontainer"), new MediaContainerItemHandler.CapabilityProvider(be));
        }
    }
}
