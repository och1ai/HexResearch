package name.dashkal.minecraft.hexresearch.fabric.xplat;

import name.dashkal.minecraft.hexresearch.effect.MindFatigueEffect;
import name.dashkal.minecraft.hexresearch.fabric.effect.MindFatigueEffectImpl;
import name.dashkal.minecraft.hexresearch.xplat.XPlatAPI;

public class FabricXPlatAPIImpl extends XPlatAPI {
    public static void init() {
        XPlatAPI.INSTANCE = new FabricXPlatAPIImpl();
    }

    @Override
    public MindFatigueEffect getMindFatigueEffect() {
        return MindFatigueEffectImpl.INSTANCE;
    }
}
