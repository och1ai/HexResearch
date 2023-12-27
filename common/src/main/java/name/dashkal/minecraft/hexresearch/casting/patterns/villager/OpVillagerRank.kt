package name.dashkal.minecraft.hexresearch.casting.patterns.villager

import at.petrak.hexcasting.api.spell.ConstMediaAction
import at.petrak.hexcasting.api.spell.asActionResult
import at.petrak.hexcasting.api.spell.casting.CastingContext
import at.petrak.hexcasting.api.spell.getEntity
import at.petrak.hexcasting.api.spell.iota.Iota
import at.petrak.hexcasting.api.spell.mishaps.MishapBadEntity
import net.minecraft.network.chat.Component
import net.minecraft.world.entity.npc.Villager

class OpVillagerRank : ConstMediaAction {
    override val argc: Int = 1
    override val mediaCost: Int = 0

    override fun execute(args: List<Iota>, ctx: CastingContext): List<Iota> {
        val target = args.getEntity(0, argc)
        ctx.assertEntityInRange(target)
        if (target !is Villager) throw MishapBadEntity(
            target,
            Component.translatable("text.hexresearch.villager_actions.villager")
        )

        return target.villagerData.level.asActionResult
    }
}
