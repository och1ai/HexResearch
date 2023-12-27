package name.dashkal.minecraft.hexresearch.casting.mishaps

import at.petrak.hexcasting.api.misc.FrozenColorizer
import at.petrak.hexcasting.api.spell.casting.CastingContext
import at.petrak.hexcasting.api.spell.casting.ResolvedPatternType
import at.petrak.hexcasting.api.spell.iota.GarbageIota
import at.petrak.hexcasting.api.spell.iota.Iota
import at.petrak.hexcasting.api.spell.mishaps.Mishap
import at.petrak.hexcasting.api.utils.asTranslatedComponent
import net.minecraft.network.chat.Component
import net.minecraft.world.item.DyeColor

class MishapNotPerWorldPattern() : Mishap() {
    override fun accentColor(ctx: CastingContext, errorCtx: Context): FrozenColorizer =
        dyeColor(DyeColor.YELLOW)

    override fun resolutionType(ctx: CastingContext): ResolvedPatternType =
        ResolvedPatternType.UNRESOLVED

    override fun errorMessage(ctx: CastingContext, errorCtx: Context): Component =
        "hexresearch.mishap.not_per_world_pattern".asTranslatedComponent

    override fun execute(ctx: CastingContext, errorCtx: Context, stack: MutableList<Iota>) {
        stack.add(GarbageIota())
    }
}
