package name.dashkal.minecraft.hexresearch.casting.patterns.spells

import at.petrak.hexcasting.api.misc.MediaConstants
import at.petrak.hexcasting.api.spell.ParticleSpray
import at.petrak.hexcasting.api.spell.RenderedSpell
import at.petrak.hexcasting.api.spell.SpellAction
import at.petrak.hexcasting.api.spell.casting.CastingContext
import at.petrak.hexcasting.api.spell.getEntity
import at.petrak.hexcasting.api.spell.iota.Iota
import at.petrak.hexcasting.api.spell.mishaps.MishapBadEntity
import name.dashkal.minecraft.hexresearch.casting.mishaps.MishapNotCaptureShard
import name.dashkal.minecraft.hexresearch.item.ItemCaptureShard
import name.dashkal.minecraft.hexresearch.util.Option
import net.minecraft.network.chat.Component
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.item.ItemEntity
import net.minecraft.world.item.ItemStack

class OpUncrystallizeLife : SpellAction {
    override val argc: Int = 1
    val cost: Int = 10 * MediaConstants.DUST_UNIT

    override fun execute(args: List<Iota>, ctx: CastingContext): Triple<RenderedSpell, Int, List<ParticleSpray>>? {
        val target = args.getEntity(0, argc)

        ctx.assertEntityInRange(target)

        if (target is ItemEntity) {
            val item = target.item.item
            if (item is ItemCaptureShard) {
                if (item.isFilled(target.item)) {
                    return Triple(
                        Spell(target, target.item, item),
                        cost,
                        listOf(
                            ParticleSpray.burst(target.position(), 1.0)
                        )
                    )
                }
            }
        }

        throw MishapNotCaptureShard(Option.none(), true)
    }

    private data class Spell(val entity: ItemEntity, val itemStack: ItemStack, val item: ItemCaptureShard) : RenderedSpell {
        override fun cast(ctx: CastingContext) {
            if (item.release(itemStack, ctx.world, entity.position(), ctx.caster.isCreative)) {
                if (!ctx.caster.isCreative) {
                    entity.remove(Entity.RemovalReason.DISCARDED)
                }
            }
        }
    }
}
