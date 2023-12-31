package name.dashkal.minecraft.hexresearch.forge.effect;

import name.dashkal.minecraft.hexresearch.effect.MindFatigueEffect;
import net.minecraft.world.item.ItemStack;

import java.util.List;

/**
 * Forge specific MindFatigueEffect that does not allow any curative items.
 */
public class MindFatigueEffectImpl extends MindFatigueEffect {
    public static final MindFatigueEffectImpl INSTANCE = new MindFatigueEffectImpl();

    public MindFatigueEffectImpl() {
        super();
    }

    @Override
    public List<ItemStack> getCurativeItems() {
        return List.of();
    }
}
