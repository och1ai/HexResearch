package name.dashkal.minecraft.hexresearch.util;

import at.petrak.hexcasting.api.PatternRegistry;
import at.petrak.hexcasting.api.spell.math.HexDir;
import com.mojang.datafixers.util.Pair;
import name.dashkal.minecraft.hexresearch.HexResearch;
import name.dashkal.minecraft.hexresearch.registry.HRHexPatterns;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.storage.DimensionDataStorage;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class PatternFixer {
    public static void ensurePerWorldPatterns(ServerLevel serverLevel) {
        Map<String, Pair<ResourceLocation, HexDir>> patterns = PatternRegistry.getPerWorldPatterns(serverLevel);
        Set<ResourceLocation> perWorldPatternIds = patterns.values().stream().map(Pair::getFirst).collect(Collectors.toSet());

        // If we do not have all our per-world spells, force a recalculation
        if (!perWorldPatternIds.containsAll(HRHexPatterns.PER_WORLD_PATTERN_IDS)) {
            if (HexResearch.getServerConfig().patternConfig().forceRecalculateMissing()) {
                HexResearch.LOGGER.warn("Hex Casting Pattern Registry does not have our per-world patterns! Forcing regeneration.");
                ServerLevel overWorld = serverLevel.getServer().overworld();
                DimensionDataStorage ds = overWorld.getDataStorage();
                ds.set(PatternRegistry.TAG_SAVED_DATA, PatternRegistry.Save.create(overWorld.getSeed()));
            } else {
                HexResearch.LOGGER.warn("Hex Casting Pattern Registry does not have our per-world patterns! Run the command /hexcasting recalcPatterns to regenerate the registry.");
            }
        }
    }
}
