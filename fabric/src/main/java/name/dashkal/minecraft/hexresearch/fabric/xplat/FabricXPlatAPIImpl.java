package name.dashkal.minecraft.hexresearch.fabric.xplat;

import name.dashkal.minecraft.hexresearch.HexResearch;
import name.dashkal.minecraft.hexresearch.effect.MindFatigueEffect;
import name.dashkal.minecraft.hexresearch.fabric.cc.HexResearchCC;
import name.dashkal.minecraft.hexresearch.fabric.effect.MindFatigueEffectImpl;
import name.dashkal.minecraft.hexresearch.xplat.XPlatAPI;
import net.minecraft.world.entity.npc.Villager;

import java.util.SortedSet;

public class FabricXPlatAPIImpl extends XPlatAPI {
    public static void init() {
        XPlatAPI.instance = new FabricXPlatAPIImpl();
    }

    @Override
    public MindFatigueEffect getMindFatigueEffect() {
        return MindFatigueEffectImpl.INSTANCE;
    }

    @Override
    public void cognitiveInducerMarkVillager(Villager villager, long gameTime) {
        HexResearchCC.COGNITIVE_INDUCER_MARK.get(villager).mark(gameTime);
    }

    @Override
    public SortedSet<Long> cognitiveInducerGetMarks(Villager villager) {
        return HexResearchCC.COGNITIVE_INDUCER_MARK.get(villager).getMarks();
    }

    @Override
    public void cognitiveInducerPruneMarks(Villager villager, long gameTime) {
        HexResearchCC.COGNITIVE_INDUCER_MARK.get(villager).pruneMarks(gameTime);
    }
}
