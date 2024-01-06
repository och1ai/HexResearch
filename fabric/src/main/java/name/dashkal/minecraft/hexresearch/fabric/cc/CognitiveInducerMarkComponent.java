package name.dashkal.minecraft.hexresearch.fabric.cc;

import dev.onyxstudios.cca.api.v3.component.ComponentV3;

public interface CognitiveInducerMarkComponent extends ComponentV3 {
    /** Returns the time (as per `ServerLevel.getGameTime()` until the mark expires */
    long getExpirationTimeTicks();

    /** Returns the time (as per `ServerLevel.getGameTime()` until the mark expires */
    void setMark(long expirationTimeTicks);
}
