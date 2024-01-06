package name.dashkal.minecraft.hexresearch.forge.xplat;

import name.dashkal.minecraft.hexresearch.HexResearch;
import name.dashkal.minecraft.hexresearch.block.entity.CognitiveInducerBlockEntity;
import name.dashkal.minecraft.hexresearch.effect.MindFatigueEffect;
import name.dashkal.minecraft.hexresearch.forge.effect.MindFatigueEffectImpl;
import name.dashkal.minecraft.hexresearch.xplat.XPlatAPI;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.npc.Villager;

public class ForgeXPlatAPIImpl extends XPlatAPI {
    public static void init() {
        XPlatAPI.INSTANCE = new ForgeXPlatAPIImpl();
    }

    @Override
    public MindFatigueEffect getMindFatigueEffect() {
        return MindFatigueEffectImpl.INSTANCE;
    }

    @Override
    public void cognitiveInducerMarkVillager(Villager villager, long expirationTime) {
        if (!villager.getPersistentData().contains(HexResearch.MOD_ID)) {
            villager.getPersistentData().put(HexResearch.MOD_ID, new CompoundTag());
        }
        villager.getPersistentData()
                .getCompound(HexResearch.MOD_ID)
                .putLong(CognitiveInducerBlockEntity.TAG_VILLAGER_MARKED, expirationTime);
    }

    @Override
    public boolean cognitiveInducerIsVillagerMarked(Villager villager, long worldTime) {
        long exprTime = villager.getPersistentData()
                .getCompound(HexResearch.MOD_ID)
                .getLong(CognitiveInducerBlockEntity.TAG_VILLAGER_MARKED);

        return exprTime > worldTime;
    }
}
