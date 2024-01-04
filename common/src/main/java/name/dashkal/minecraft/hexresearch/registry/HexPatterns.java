package name.dashkal.minecraft.hexresearch.registry;

import at.petrak.hexcasting.api.PatternRegistry;
import at.petrak.hexcasting.api.spell.Action;
import at.petrak.hexcasting.api.spell.math.HexDir;
import at.petrak.hexcasting.api.spell.math.HexPattern;
import kotlin.Triple;
import name.dashkal.minecraft.hexresearch.casting.patterns.spells.OpThoughtSieve;
import name.dashkal.minecraft.hexresearch.casting.patterns.villager.OpVillagerPopularity;
import name.dashkal.minecraft.hexresearch.casting.patterns.villager.OpVillagerRank;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

import static name.dashkal.minecraft.hexresearch.HexResearch.id;

public class HexPatterns {
    public static List<Triple<HexPattern, ResourceLocation, Action>> PATTERNS = new ArrayList<>();
    public static List<Triple<HexPattern, ResourceLocation, Action>> PER_WORLD_PATTERNS = new ArrayList<>();

    // IMPORTANT: be careful to keep the registration calls looking like this, or you'll have to edit the hexdoc pattern regex.
    public static HexPattern THOUGHT_SIEVE = register(HexPattern.fromAngles("qadaadadqaqdadqaq", HexDir.WEST), "thought_sieve", new OpThoughtSieve());
    public static HexPattern VILLAGER_RANK = register(HexPattern.fromAngles("qadaadaeawa", HexDir.WEST), "villager_rank", new OpVillagerRank());
    public static HexPattern VILLAGER_REPUTATION = register(HexPattern.fromAngles("qadaadadeee", HexDir.WEST), "villager_popularity", new OpVillagerPopularity());

    // Test/Debug/Marked for removal
/*
    public static HexPattern PATTERNS_MATCH_SHAPE = register(HexPattern.fromAngles("add", HexDir.EAST), "patterns_match_shape", new OpPatternsMatchShape());
    public static HexPattern MIND_HARM = register(HexPattern.fromAngles("qaa", HexDir.SOUTH_EAST), "mind_harm", new OpMindHarm());
*/

    public static void init() {
        try {
            for (Triple<HexPattern, ResourceLocation, Action> patternTriple : PATTERNS) {
                PatternRegistry.mapPattern(patternTriple.getFirst(), patternTriple.getSecond(), patternTriple.getThird());
            }
            for (Triple<HexPattern, ResourceLocation, Action> patternTriple : PER_WORLD_PATTERNS) {
                PatternRegistry.mapPattern(patternTriple.getFirst(), patternTriple.getSecond(), patternTriple.getThird(), true);
            }
        } catch (PatternRegistry.RegisterPatternException e) {
            e.printStackTrace();
        }
    }

    private static HexPattern register(HexPattern pattern, String name, Action action) {
        Triple<HexPattern, ResourceLocation, Action> triple = new Triple<>(pattern, id(name), action);
        PATTERNS.add(triple);
        return pattern;
    }

    private static HexPattern registerPerWorld(HexPattern pattern, String name, Action action) {
        Triple<HexPattern, ResourceLocation, Action> triple = new Triple<>(pattern, id(name), action);
        PER_WORLD_PATTERNS.add(triple);
        return pattern;
    }
}
