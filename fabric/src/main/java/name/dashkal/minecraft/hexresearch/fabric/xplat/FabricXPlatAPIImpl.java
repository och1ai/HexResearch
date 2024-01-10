package name.dashkal.minecraft.hexresearch.fabric.xplat;

import name.dashkal.minecraft.hexresearch.effect.MindFatigueEffect;
import name.dashkal.minecraft.hexresearch.fabric.cc.CognitiveInducerMarkComponent;
import name.dashkal.minecraft.hexresearch.fabric.cc.HexResearchCC;
import name.dashkal.minecraft.hexresearch.fabric.effect.MindFatigueEffectImpl;
import name.dashkal.minecraft.hexresearch.xplat.XPlatAPI;
import net.minecraft.world.entity.npc.Villager;

public class FabricXPlatAPIImpl extends XPlatAPI {
    public static void init() {
        XPlatAPI.instance = new FabricXPlatAPIImpl();
    }

    @Override
    public MindFatigueEffect getMindFatigueEffect() {
        return MindFatigueEffectImpl.INSTANCE;
    }

    @Override
    public void cognitiveInducerMarkVillager(Villager villager, long expirationTime) {
        HexResearchCC.COGNITIVE_INDUCER_MARK.get(villager).setMark(expirationTime);
    }

    @Override
    public boolean cognitiveInducerIsVillagerMarked(Villager villager, long worldTime) {
        CognitiveInducerMarkComponent c = HexResearchCC.COGNITIVE_INDUCER_MARK.get(villager);
        return c.getExpirationTimeTicks() > worldTime;
    }
}
