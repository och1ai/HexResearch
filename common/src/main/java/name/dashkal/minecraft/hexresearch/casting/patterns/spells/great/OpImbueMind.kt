package name.dashkal.minecraft.hexresearch.casting.patterns.spells.great

import at.petrak.hexcasting.api.misc.MediaConstants
import at.petrak.hexcasting.api.spell.ParticleSpray
import at.petrak.hexcasting.api.spell.RenderedSpell
import at.petrak.hexcasting.api.spell.SpellAction
import at.petrak.hexcasting.api.spell.casting.CastingContext
import at.petrak.hexcasting.api.spell.getBlockPos
import at.petrak.hexcasting.api.spell.iota.Iota
import at.petrak.hexcasting.api.spell.mishaps.MishapBadBlock
import at.petrak.hexcasting.api.utils.asTranslatedComponent
import at.petrak.hexcasting.common.recipe.BrainsweepRecipe
import at.petrak.hexcasting.common.recipe.HexRecipeStuffRegistry
import name.dashkal.minecraft.hexresearch.block.entity.CognitiveInducerBlockEntity
import name.dashkal.minecraft.hexresearch.casting.mishaps.MishapBadMindImbue
import name.dashkal.minecraft.hexresearch.interop.Interop
import name.dashkal.minecraft.hexresearch.util.Mind
import net.minecraft.core.BlockPos
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.phys.Vec3
import kotlin.jvm.optionals.getOrNull

class OpImbueMind : SpellAction {
    override val argc: Int = 2
    val cost: Int = 10 * MediaConstants.CRYSTAL_UNIT

    override fun execute(args: List<Iota>, ctx: CastingContext): Triple<RenderedSpell, Int, List<ParticleSpray>>? {
        val sourceMindPos = args.getBlockPos(0, argc)
        val targetBlockPos = args.getBlockPos(1, argc)
        ctx.assertVecInRange(sourceMindPos)
        ctx.assertVecInRange(targetBlockPos)

        // Confirm we have a source cognitive inducer
        val artificialMind = ctx.world.getBlockEntity(sourceMindPos)
        if (artificialMind == null || artificialMind !is CognitiveInducerBlockEntity) {
            throw MishapBadBlock(sourceMindPos, "text.hexresearch.imbue_mind.cognitive_inducer".asTranslatedComponent)
        }
        // Confirm the Cognitive Inducer has an artificial mind to provide
        val mind = artificialMind.impressedMind.getOrNull()
            ?: throw MishapBadBlock(sourceMindPos, "text.hexresearch.imbue_mind.cognitive_inducer".asTranslatedComponent)

        // Copied from OpBrainsweep - I should figure out what this means.
        if (!ctx.canEditBlockAt(targetBlockPos)) {
            return null
        }

        // Grab the target block
        val state = ctx.world.getBlockState(targetBlockPos)

        // HexGloop Interop
        val r = Interop.getHexGloop().offerMind(ctx, targetBlockPos, mind);
        val spell = if (r.isPresent) {
            // HexGloop accepted it
            InteropSpell(artificialMind, r.get())
        } else {
            // Look for an applicable recipe or mishap if we fail
            val recipes = ctx.world.recipeManager.getAllRecipesFor(HexRecipeStuffRegistry.BRAINSWEEP_TYPE)
            val recipe = recipes.find(checkBrainsweepRecipe(state, mind))
                ?: throw MishapBadMindImbue(artificialMind, targetBlockPos)

            // Success, return the spell
            Spell(artificialMind, targetBlockPos, state, recipe)
        }

        return Triple(
            spell,
            cost,
            listOf(
                ParticleSpray.cloud(Vec3.atCenterOf(sourceMindPos), 1.0),
                ParticleSpray.burst(Vec3.atCenterOf(targetBlockPos), 0.3, 100)
            )
        )
    }

    /**
     * Re-implementation of `BrainsweepRecipe.test` that accepts a Mind rather than a Villager
     */
    private fun checkBrainsweepRecipe(state: BlockState, mind: Mind): (BrainsweepRecipe) -> Boolean {
        return { recipe ->
            val recipeProfession = recipe.villagerIn.profession
            val recipeBiome = recipe.villagerIn.biome

            recipe.blockIn.test(state)
                    && (recipeProfession == null || recipeProfession == mind.profession)
                    && (recipeBiome == null || recipeBiome == mind.biome)
                    && (recipe.villagerIn.minLevel <= mind.rank)
        }
    }

    /**
     * Normal operation spell - Imbue the source mind into the target block using the brainsweep recipe.
     */
    private data class Spell(
        val sourceMind: CognitiveInducerBlockEntity,
        val targetBlockPos: BlockPos,
        val state: BlockState,
        val recipe: BrainsweepRecipe
    ) : RenderedSpell {
        override fun cast(ctx: CastingContext) {
            sourceMind.clearMind()
            ctx.world.setBlockAndUpdate(targetBlockPos, BrainsweepRecipe.copyProperties(state, recipe.result))
        }
    }

    /**
     * Interop spell - Clear the source mind and invoke the returned runnable.
     */
    private data class InteropSpell(
        val sourceMind: CognitiveInducerBlockEntity,
        val accept: Runnable
    ) : RenderedSpell {
        override fun cast(ctx: CastingContext) {
            sourceMind.clearMind()
            accept.run()
        }
    }
}
