package name.dashkal.minecraft.hexresearch.config;

import name.dashkal.minecraft.hexresearch.block.entity.CognitiveInducerBlockEntity;

import java.util.Map;

/**
 * Server specific configuration for Hex Research
 */
@SuppressWarnings("FieldMayBeFinal")
public class ServerConfig {
    private MindTrainingConfig mindTrainingConfig = new MindTrainingConfig();
    private PatternConfig patternConfig = new PatternConfig();

    /**
     * Creates a new ServerConfig with default values
     */
    public ServerConfig() {}

    /**
     * Creates a new ServerConfig
     */
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
        int impressionCostDust = 5;
        int impressionMarkExpirationTimeSeconds = 60 * 60;
        Map<Integer, Integer> requiredRankImpressions = Map.of(
                1, 5,
                2, 10,
                3, 15,
                4, 20,
                5, 25
        );

        /**
         * Creates a MindTrainingConfig object with default values
         */
        public MindTrainingConfig() {}

        /**
         * Creates a new MindTrainingConfig
         *
         * @param requiredRankImpressions a map from villager ranks to the number of impressions required to produce a mind of that rank
         * @param impressionMarkExpirationTimeSeconds the time in seconds until a mark from the cognitive inducer expires (Minimum 30)
         * @param impressionCostDust the cost per impression in units of amethyst dust
         */
        public MindTrainingConfig(int impressionCostDust, int impressionMarkExpirationTimeSeconds, Map<Integer, Integer> requiredRankImpressions) {
            this.impressionCostDust = impressionCostDust;
            this.impressionMarkExpirationTimeSeconds = Math.max(CognitiveInducerBlockEntity.IMPRESSION_COOLDOWN_SECONDS, impressionMarkExpirationTimeSeconds);
            this.requiredRankImpressions = requiredRankImpressions;
        }

        public int impressionCostDust() {
            return impressionCostDust;
        }

        public Map<Integer, Integer> requiredRankImpressions() {
            return requiredRankImpressions;
        }

        public int impressionMarkExpirationTimeSeconds() {
            // We're using Gson magic right now for fabric configs, which can set this value under the minimum, so we clamp it again here.
            return Math.max(CognitiveInducerBlockEntity.IMPRESSION_COOLDOWN_SECONDS, impressionMarkExpirationTimeSeconds);
        }
    }

    /**
     * Configuration for pattern logic
     */
    public static class PatternConfig {
        boolean forceRecalculateMissing = false;

        /**
         * Creates a new PatternConfig with default values
         */
        public PatternConfig() {}

        /**
         * Creates a new PatternConfig
         *
         * @param forceRecalculateMissing if <code>true</code>, force HexCasting to regenerate its pattern registry if our per-world patterns are missing
         */
        public PatternConfig(boolean forceRecalculateMissing) {
            this.forceRecalculateMissing = forceRecalculateMissing;
        }

        public boolean forceRecalculateMissing() {
            return forceRecalculateMissing;
        }
    }

    /**
     * Returns the default configuration.
     */
    public static ServerConfig getDefault() {
        return new ServerConfig();
    }
}
