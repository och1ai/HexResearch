package name.dashkal.minecraft.hexresearch.casting.patterns.spells

import at.petrak.hexcasting.api.misc.MediaConstants
import at.petrak.hexcasting.api.spell.*
import at.petrak.hexcasting.api.spell.casting.CastingContext
import at.petrak.hexcasting.api.spell.iota.Iota
import at.petrak.hexcasting.api.spell.mishaps.MishapBadBlock
import at.petrak.hexcasting.api.utils.asTranslatedComponent
import name.dashkal.minecraft.hexresearch.block.entity.CognitiveInducerBlockEntity
import net.minecraft.core.Registry
import net.minecraft.world.entity.npc.Villager
import net.minecraft.world.phys.Vec3

class OpCloneMind : SpellAction {
    override val argc: Int = 2
    val cost: Int = MediaConstants.CRYSTAL_UNIT

    override fun execute(args: List<Iota>, ctx: CastingContext): Triple<RenderedSpell, Int, List<ParticleSpray>>? {
        val sourceMind = args.getVillager(0, argc)
        val targetMindPos = args.getBlockPos(1, argc)
        ctx.assertEntityInRange(sourceMind)
        ctx.assertVecInRange(targetMindPos)

        val targetMind = ctx.world.getBlockEntity(targetMindPos)
        if (targetMind == null || targetMind !is CognitiveInducerBlockEntity) {
            throw MishapBadBlock(targetMindPos, "text.hexresearch.imbue_mind.artificial_mind".asTranslatedComponent)
        }
        if (!ctx.canEditBlockAt(targetMindPos)) {
            return null
        }

        return Triple(
            Spell(sourceMind, targetMind),
            cost,
            listOf(
                ParticleSpray.cloud(sourceMind.position(), 1.0),
                ParticleSpray.burst(Vec3.atCenterOf(targetMindPos), 0.3, 100)
            )
        )
    }

    private data class Spell(
        val sourceMind: Villager,
        val targetMind: CognitiveInducerBlockEntity
    ) : RenderedSpell {
        override fun cast(ctx: CastingContext) {
            targetMind.forceMind(
                Registry.VILLAGER_PROFESSION.getKey(sourceMind.villagerData.profession),
                Registry.VILLAGER_TYPE.getKey(sourceMind.villagerData.type),
                sourceMind.villagerData.level
            )
        }
    }
}
