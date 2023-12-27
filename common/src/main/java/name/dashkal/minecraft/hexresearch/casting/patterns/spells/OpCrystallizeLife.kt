package name.dashkal.minecraft.hexresearch.casting.patterns.spells

import at.petrak.hexcasting.api.misc.MediaConstants
import at.petrak.hexcasting.api.spell.ParticleSpray
import at.petrak.hexcasting.api.spell.RenderedSpell
import at.petrak.hexcasting.api.spell.SpellAction
import at.petrak.hexcasting.api.spell.casting.CastingContext
import at.petrak.hexcasting.api.spell.getEntity
import at.petrak.hexcasting.api.spell.iota.Iota
import at.petrak.hexcasting.api.spell.mishaps.MishapBadEntity
import at.petrak.hexcasting.api.spell.mishaps.MishapBadItem
import at.petrak.hexcasting.api.utils.asTranslatedComponent
import name.dashkal.minecraft.hexresearch.casting.mishaps.MishapNotCaptureShard
import name.dashkal.minecraft.hexresearch.item.ItemCaptureShard
import name.dashkal.minecraft.hexresearch.util.Option
import net.minecraft.world.InteractionHand
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.item.ItemStack

class OpCrystallizeLife : SpellAction {
    override val argc: Int = 1
    val cost: Int = 10 * MediaConstants.DUST_UNIT

    override fun execute(args: List<Iota>, ctx: CastingContext): Triple<RenderedSpell, Int, List<ParticleSpray>>? {
        val target = args.getEntity(0, argc)

        ctx.assertEntityInRange(target)

        if (target !is LivingEntity) {
            throw MishapBadEntity(
                target,
                "text.hexresearch.crystallize_life.living".asTranslatedComponent
            )
        }

        val (shard, hand) = ctx.getHeldItemToOperateOn(this::isEmptyShard)

        // Need to double-check the shard because getHeldItemToOperateOn returns the off-hand item if neither item is accepted
        val item = shard.item
        if (item is ItemCaptureShard) {
            if (!item.isFilled(shard)) {
                return Triple (
                    Spell(target, shard, item, hand),
                    cost,
                    listOf(
                        ParticleSpray.burst(target.position(), 1.0)
                    )
                )
            }
        }

        throw MishapNotCaptureShard(Option.some(hand), false)
    }

    private fun isEmptyShard(itemStack: ItemStack): Boolean {
        val item = itemStack.item
        return if (item is ItemCaptureShard) {
            !item.isFilled(itemStack)
        } else {
            false
        }
    }

    private data class Spell(val entity: LivingEntity, val shard: ItemStack, val item: ItemCaptureShard, val hand: InteractionHand) : RenderedSpell {
        override fun cast(ctx: CastingContext) {
            item.capture(shard, entity)
        }
    }
}
