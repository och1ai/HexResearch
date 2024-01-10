package name.dashkal.minecraft.hexresearch.casting.mishaps

import at.petrak.hexcasting.api.misc.FrozenColorizer
import at.petrak.hexcasting.api.spell.casting.CastingContext
import at.petrak.hexcasting.api.spell.iota.Iota
import at.petrak.hexcasting.api.spell.mishaps.Mishap
import at.petrak.hexcasting.api.utils.asTranslatedComponent
import name.dashkal.minecraft.hexresearch.util.Option
import name.dashkal.minecraft.hexresearch.util.Some
import net.minecraft.network.chat.Component
import net.minecraft.world.InteractionHand
import net.minecraft.world.item.DyeColor

class MishapNotCaptureShard(val hand: Option<InteractionHand>, val shouldBeFilled: Boolean) : Mishap() {
    override fun accentColor(ctx: CastingContext, errorCtx: Context): FrozenColorizer =
        dyeColor(DyeColor.BROWN)

    override fun errorMessage(ctx: CastingContext, errorCtx: Context): Component {
        return if (shouldBeFilled) {
            "hexresearch.mishap.uncrystallize_life.filled".asTranslatedComponent
        } else {
            "hexresearch.mishap.not_capture_shard.empty".asTranslatedComponent
        }
    }

    override fun execute(ctx: CastingContext, errorCtx: Context, stack: MutableList<Iota>) {
        if (hand is Some) {
            yeetHeldItem(ctx, hand.value)
        } else {
            yeetHeldItem(ctx, ctx.castingHand)
        }
    }
}
