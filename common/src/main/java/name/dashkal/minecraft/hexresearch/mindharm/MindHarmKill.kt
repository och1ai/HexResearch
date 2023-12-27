package name.dashkal.minecraft.hexresearch.mindharm

import at.petrak.hexcasting.api.misc.HexDamageSources
import at.petrak.hexcasting.api.spell.mishaps.Mishap
import name.dashkal.minecraft.hexresearch.HexResearch
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.npc.Villager

/** The villager is killed outright */
class MindHarmKill : MindHarmMechanic {
    override fun getId(): ResourceLocation = ResourceLocation(HexResearch.MOD_ID, "mindharm_kill")

    override fun getSeverity(): MindHarmSeverity = MindHarmSeverity.DEATH

    override fun doHarm(attacker: Entity?, villager: Villager): Boolean {
        // Kill the villager the same way a botched brainweep does
        Mishap.trulyHurt(villager, HexDamageSources.overcastDamageFrom(attacker ?: villager), villager.health)

        return villager.isDeadOrDying;
    }
}
