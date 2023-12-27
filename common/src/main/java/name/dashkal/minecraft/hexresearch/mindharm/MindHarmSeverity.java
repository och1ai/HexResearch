package name.dashkal.minecraft.hexresearch.mindharm;

import java.util.Comparator;

public enum MindHarmSeverity {
    /** Harms that can be reversed without need for magic */
    REVERSIBLE,
    /** Harms that impair the villager, but do not destroy its ability to function. */
    PERMANENT_MINOR,
    /** Harms that permanently impair the villager, making it unable to function. */
    PERMANENT_MAJOR,
    /** Harms that kill the villager. */
    DEATH
}
