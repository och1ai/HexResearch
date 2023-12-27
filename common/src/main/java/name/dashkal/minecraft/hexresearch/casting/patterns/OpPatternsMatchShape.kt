package name.dashkal.minecraft.hexresearch.casting.patterns

import at.petrak.hexcasting.api.spell.ConstMediaAction
import at.petrak.hexcasting.api.spell.asActionResult
import at.petrak.hexcasting.api.spell.casting.CastingContext
import at.petrak.hexcasting.api.spell.getPattern
import at.petrak.hexcasting.api.spell.iota.Iota
import name.dashkal.minecraft.hexresearch.util.HexPatternMatch

class OpPatternsMatchShape : ConstMediaAction {
    override val argc: Int = 2

    override fun execute(args: List<Iota>, ctx: CastingContext): List<Iota> {
        val p1 = args.getPattern(0, argc)
        val p2 = args.getPattern(1, argc)

        return HexPatternMatch.shapeMatches(p1, p2).asActionResult;
    }
}
