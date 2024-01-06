package name.dashkal.minecraft.hexresearch.config;

import java.util.Map;

/**
 * Server specific configuration for Hex Research
 */
public record ServerConfig(MindTrainingConfig mindTrainingConfig, PatternConfig patternConfig) {
    /**
     * Configuration for mind training (The Cognitive Inducer)
     *
     * @param requiredRankImpressions a map from villager ranks to the number of impressions required to produce a mind of that rank
     * @param impressionCostDust the cost per impression in units of amethyst dust
     */
    public record MindTrainingConfig(Map<Integer, Integer> requiredRankImpressions, int impressionCostDust) {}

    /**
     * Configuration for pattern logic
     *
     * @param forceRecalcMissing if <code>true</code>, force HexCasting to regenerate its pattern registry if our per-world patterns are missing
     */
    public record PatternConfig(boolean forceRecalcMissing) {}

    /**
     * Returns the default configuration.
     */
    public static ServerConfig getDefault() {
        return new ServerConfig(
                new MindTrainingConfig(
                    Map.of(
                            1, 5,
                            2, 10,
                            3, 15,
                            4, 20,
                            5, 25
                    ),
                5
                ),
                new PatternConfig(false)
        );
    }
}
