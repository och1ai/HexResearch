package name.dashkal.minecraft.hexresearch.forge.event;

import name.dashkal.minecraft.hexresearch.block.entity.AbstractMediaContainerBlockEntity;
import name.dashkal.minecraft.hexresearch.forge.cap.CognitiveInducerMarksImpl;
import name.dashkal.minecraft.hexresearch.forge.cap.ICognitiveInducerMarks;
import name.dashkal.minecraft.hexresearch.forge.cap.MediaContainerItemHandler;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;

public class CapabilityEventHandler {
    public static void init() {
        MinecraftForge.EVENT_BUS.addListener(CapabilityEventHandler::registerCapabilities);
        MinecraftForge.EVENT_BUS.addGenericListener(BlockEntity.class, CapabilityEventHandler::onAttachCapabilitiesBlockEntity);
        MinecraftForge.EVENT_BUS.addGenericListener(Entity.class, CapabilityEventHandler::onAttachCapabilitiesEntity);
    }

    public static void registerCapabilities(RegisterCapabilitiesEvent ev) {
        ev.register(ICognitiveInducerMarks.class);
    }

    public static void onAttachCapabilitiesBlockEntity(AttachCapabilitiesEvent<BlockEntity> event) {
        if (event.getObject() instanceof AbstractMediaContainerBlockEntity be) {
            event.addCapability(MediaContainerItemHandler.ID, new MediaContainerItemHandler.CapabilityProvider(be));
        }
    }

    public static void onAttachCapabilitiesEntity(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof Villager) {
            event.addCapability(ICognitiveInducerMarks.ID, new CognitiveInducerMarksImpl.CIMCapabilityProvider());
        }
    }
}
