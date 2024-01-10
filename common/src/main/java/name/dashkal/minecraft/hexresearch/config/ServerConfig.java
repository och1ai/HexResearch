package name.dashkal.minecraft.hexresearch.config;

import java.util.Map;

/**
 * Server specific configuration for Hex Research
 */
public class ServerConfig {
    private MindTrainingConfig mindTrainingConfig;
    private PatternConfig patternConfig;

    public ServerConfig(MindTrainingConfig mindTrainingConfig, PatternConfig patternConfig) {
        this.mindTrainingConfig = mindTrainingConfig;
        this.patternConfig = patternConfig;
    }

    public MindTrainingConfig mindTrainingConfig() {
        return mindTrainingConfig;
    }

    public PatternConfig patternConfig() {
        return patternConfig;
    }

    /**
     * Configuration for mind training (The Cognitive Inducer)
     */
    public static class MindTrainingConfig {
        int impressionCostDust;
        Map<Integer, Integer> requiredRankImpressions;

        /**
         * @param requiredRankImpressions a map from villager ranks to the number of impressions required to produce a mind of that rank
         * @param impressionCostDust the cost per impression in units of amethyst dust
         */
        public MindTrainingConfig(int impressionCostDust, Map<Integer, Integer> requiredRankImpressions) {
            this.impressionCostDust = impressionCostDust;
            this.requiredRankImpressions = requiredRankImpressions;
        }

        public int impressionCostDust() {
            return impressionCostDust;
        }

        public Map<Integer, Integer> requiredRankImpressions() {
            return requiredRankImpressions;
        }
    }

    /**
     * Configuration for pattern logic
     */
    public static class PatternConfig {
        boolean forceRecalcMissing;

        /**
         * @param forceRecalcMissing if <code>true</code>, force HexCasting to regenerate its pattern registry if our per-world patterns are missing
         */

        public PatternConfig(boolean forceRecalcMissing) {
            this.forceRecalcMissing = forceRecalcMissing;
        }

        public boolean forceRecalcMissing() {
            return forceRecalcMissing;
        }
    }

    /**
     * Returns the default configuration.
     */
    public static ServerConfig getDefault() {
        return new ServerConfig(
                new MindTrainingConfig(
                    5,
                    Map.of(
                            1, 5,
                            2, 10,
                            3, 15,
                            4, 20,
                            5, 25
                    )
                ),
                new PatternConfig(false)
        );
    }
}
