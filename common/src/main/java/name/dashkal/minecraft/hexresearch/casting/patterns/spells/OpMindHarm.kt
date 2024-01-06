package name.dashkal.minecraft.hexresearch.casting.patterns.spells

import at.petrak.hexcasting.api.misc.MediaConstants
import at.petrak.hexcasting.api.spell.ParticleSpray
import at.petrak.hexcasting.api.spell.RenderedSpell
import at.petrak.hexcasting.api.spell.SpellAction
import at.petrak.hexcasting.api.spell.casting.CastingContext
import at.petrak.hexcasting.api.spell.getEntity
import at.petrak.hexcasting.api.spell.iota.Iota
import at.petrak.hexcasting.api.spell.mishaps.MishapBadEntity
import name.dashkal.minecraft.hexresearch.mindharm.MindHarmLogic
import name.dashkal.minecraft.hexresearch.registry.HRMindHarms
import net.minecraft.network.chat.Component
import net.minecraft.server.level.ServerPlayer
import net.minecraft.world.entity.npc.Villager

class OpMindHarm : SpellAction {
    override val argc: Int = 1

    /** Cost is 10 dust */
    val cost: Int = 1 * MediaConstants.DUST_UNIT

    override fun execute(args: List<Iota>, ctx: CastingContext): Triple<RenderedSpell, Int, List<ParticleSpray>>? {
        val villager = args.getEntity(0, argc)
        ctx.assertEntityInRange(villager)

        // Check Villager
        if (villager !is Villager) throw MishapBadEntity(
            villager,
            Component.translatable("text.hexresearch.thought_sieve.villager")
        )

        // The requirements are met.  Pay the cost and invoke the spell.
        return Triple(
            Spell(villager, ctx.caster),
            cost,
            listOf(
                ParticleSpray.burst(villager.position(), 1.0)
            )
        )
    }

    private data class Spell(val villager: Villager, val caster: ServerPlayer) : RenderedSpell {
        override fun cast(ctx: CastingContext) {
            MindHarmLogic.doHarm(caster, villager, HRMindHarms.KILL.id)
        }
    }
}