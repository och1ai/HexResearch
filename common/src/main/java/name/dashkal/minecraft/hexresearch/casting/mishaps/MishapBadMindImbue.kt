package name.dashkal.minecraft.hexresearch.casting.mishaps

import at.petrak.hexcasting.api.misc.FrozenColorizer
import at.petrak.hexcasting.api.spell.ParticleSpray
import at.petrak.hexcasting.api.spell.casting.CastingContext
import at.petrak.hexcasting.api.spell.iota.Iota
import at.petrak.hexcasting.api.spell.mishaps.Mishap
import name.dashkal.minecraft.hexresearch.block.entity.CognitiveInducerBlockEntity
import net.minecraft.core.BlockPos
import net.minecraft.network.chat.Component
import net.minecraft.world.item.DyeColor
import net.minecraft.world.phys.Vec3

class MishapBadMindImbue(val artificialMind: CognitiveInducerBlockEntity, val targetPos: BlockPos) : Mishap() {
    override fun accentColor(ctx: CastingContext, errorCtx: Context): FrozenColorizer =
        dyeColor(DyeColor.GREEN)

    override fun errorMessage(ctx: CastingContext, errorCtx: Context): Component =
        error("bad_brainsweep", blockAtPos(ctx, this.targetPos))

    override fun particleSpray(ctx: CastingContext): ParticleSpray {
        return ParticleSpray.burst(Vec3.atCenterOf(targetPos), 1.0)
    }

    override fun execute(ctx: CastingContext, errorCtx: Context, stack: MutableList<Iota>) {
        artificialMind.clearMind()
    }
}
