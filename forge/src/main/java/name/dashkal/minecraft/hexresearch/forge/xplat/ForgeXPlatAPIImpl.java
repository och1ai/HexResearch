package name.dashkal.minecraft.hexresearch.forge.xplat;

import name.dashkal.minecraft.hexresearch.effect.MindFatigueEffect;
import name.dashkal.minecraft.hexresearch.forge.effect.MindFatigueEffectImpl;
import name.dashkal.minecraft.hexresearch.xplat.XPlatAPI;

public class ForgeXPlatAPIImpl extends XPlatAPI {
    public static void init() {
        XPlatAPI.INSTANCE = new ForgeXPlatAPIImpl();
    }

    @Override
    public MindFatigueEffect getMindFatigueEffect() {
        return MindFatigueEffectImpl.INSTANCE;
    }
}
