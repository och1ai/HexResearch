package name.dashkal.minecraft.hexresearch.mindharm

import name.dashkal.minecraft.hexresearch.HexResearch
import name.dashkal.minecraft.hexresearch.registry.HexResearchMindHarmRegistry
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.npc.Villager
import java.util.*
import kotlin.math.floor
import kotlin.math.log2
import kotlin.math.min
import kotlin.random.Random

object MindHarmLogic {
    /**
     * Harm a villager with one of the registered harm mechanics. Less severe mechanics are more likely to be chosen
     * than more severe mechanics.  If the chosen mechanic fails, try again with the next.
     */
    @JvmStatic fun doRandomHarm(attacker : Entity?, villager : Villager) : Boolean {
        val mechanicMap = HexResearchMindHarmRegistry.getAll()
        val mechanics = sortMechanics(mechanicMap.values)
        val startIndex = randomByHalves(mechanics.size)
        val logger = HexResearch.LOGGER

        for (i in startIndex..mechanics.size) {
            logger.info("MindHarmLogic.doRandomHarm() selected ${mechanics[i].id}")
            if (mechanics[i].doHarm(attacker, villager)) {
                logger.info("MindHarmLogic.doRandomHarm() success")
                return true
            }
        }
        logger.debug("doRandomHarm() failure")
        return false
    }

    /**
     * Harm a villager with a specific harm mechanic.
     *
     * @return <code>true</code> if the mechanic was found and was successful, false otherwise
     */
    @JvmStatic fun doHarm(attacker : Entity?, villager : Villager, harmId: ResourceLocation): Boolean {
        return HexResearchMindHarmRegistry.get(harmId)
            .map { it.doHarm(attacker, villager) }
            .orElse(false)
    }

    /**
     * Sorts the list by severity, randomizing mechanics of equal severity.
     */
    private fun sortMechanics(mechanics: Collection<MindHarmMechanic>): List<MindHarmMechanic> {
        val bySeverity = mechanics.groupBy { it.severity }
        val outList = Vector<MindHarmMechanic>()
        for (sev in MindHarmSeverity.values()) {
            val thisSev = (bySeverity[sev] ?: listOf()).shuffled()
            outList.addAll(thisSev)
        }
        return outList
    }

    /**
     * Selects a random number from 0 (inclusive) to `max` (exclusive), where each higher number is half as likely as the previous one.
     *
     * - 50% chance to select 1
     * - 25% chance to select 2
     * - 12.5% chance to select 3
     * - And so on
     *
     * Note: `max-1` and `max` are equally likely, as is required for the total probability to sum to 1.
     */
    private fun randomByHalves(max: Int): Int {
        val roll = Random.nextDouble()
        return min(max, floor(1.0 - log2(roll)).toInt()) - 1
    }
}
