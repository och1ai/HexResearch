package name.dashkal.minecraft.hexresearch.mindharm

import at.petrak.hexcasting.common.misc.Brainsweeping
import name.dashkal.minecraft.hexresearch.HexResearch
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.npc.Villager

/** The villager is brainswept (flayed) */
class MindHarmBrainsweep : MindHarmMechanic {
    val ID: ResourceLocation = ResourceLocation(HexResearch.MOD_ID, "mindharm_brainsweep")

    override fun getId(): ResourceLocation = ID

    override fun getSeverity(): MindHarmSeverity = MindHarmSeverity.PERMANENT_MAJOR

    override fun doHarm(attacker: Entity?, villager: Villager): Boolean {
        return if (!Brainsweeping.isBrainswept(villager)) {
            Brainsweeping.brainsweep(villager)
            true
        } else {
            false
        }
    }
}