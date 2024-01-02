package name.dashkal.minecraft.hexresearch.util;

import at.petrak.hexcasting.api.spell.math.HexDir;
import at.petrak.hexcasting.api.spell.math.HexPattern;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class HexPatternMatchTest {
    @Test
    void trivialIdentity() {
        HexPattern p1 = HexPattern.fromAngles("", HexDir.NORTH_EAST);
        HexPattern p2 = HexPattern.fromAngles("", HexDir.NORTH_EAST);
        assertTrue(HexPatternMatch.shapeMatches(p1, p2));
    }

    @Test
    void trivialReverse() {
        HexPattern p1 = HexPattern.fromAngles("", HexDir.NORTH_EAST);
        HexPattern p2 = HexPattern.fromAngles("", HexDir.SOUTH_WEST);
        assertTrue(HexPatternMatch.shapeMatches(p1, p2));
    }

    @Test
    void trivialAngled() {
        HexPattern p1 = HexPattern.fromAngles("", HexDir.NORTH_EAST);
        HexPattern p2 = HexPattern.fromAngles("", HexDir.WEST);
        assertTrue(HexPatternMatch.shapeMatches(p1, p2));
    }

    @Test
    void circleIdentity() {
        HexPattern p1 = HexPattern.fromAngles("eeeee", HexDir.NORTH_EAST);
        HexPattern p2 = HexPattern.fromAngles("eeeee", HexDir.NORTH_EAST);
        assertTrue(HexPatternMatch.shapeMatches(p1, p2));
    }

    @Test
    void circleReverse() {
        HexPattern p1 = HexPattern.fromAngles("eeeee", HexDir.NORTH_EAST);
        HexPattern p2 = HexPattern.fromAngles("qqqqq", HexDir.SOUTH_EAST);
        assertTrue(HexPatternMatch.shapeMatches(p1, p2));
    }

    @Test
    void circleRotated() {
        HexPattern p1 = HexPattern.fromAngles("eeeee", HexDir.NORTH_EAST);
        HexPattern p2 = HexPattern.fromAngles("eeeee", HexDir.WEST);
        assertTrue(HexPatternMatch.shapeMatches(p1, p2));
    }

    @Test
    void circleReverseRotated() {
        HexPattern p1 = HexPattern.fromAngles("eeeee", HexDir.NORTH_EAST);
        HexPattern p2 = HexPattern.fromAngles("qqqqq", HexDir.WEST);
        assertTrue(HexPatternMatch.shapeMatches(p1, p2));
    }

    /**
     * Test case for HexPatternMatch that demonstrates <a href="https://github.com/dashkal16/HexResearch/issues/3">#3</a>
     */
    @Test
    void figureEightDifferentOriginDifferentCrossingTest() {
        HexPattern p1 = HexPattern.fromAngles("eewqqqqqwee", HexDir.NORTH_EAST);
        HexPattern p2 = HexPattern.fromAngles("eeeeaeeeeea", HexDir.WEST);
        assertTrue(HexPatternMatch.shapeMatches(p1, p2));
    }
}
