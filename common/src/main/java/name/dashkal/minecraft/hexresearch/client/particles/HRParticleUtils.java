package name.dashkal.minecraft.hexresearch.client.particles;

import at.petrak.hexcasting.api.addldata.ADColorizer;
import at.petrak.hexcasting.common.particles.ConjureParticleOptions;
import net.minecraft.client.Minecraft;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class HRParticleUtils {
    public static void spawnHexConjuredParticles(Level level, Vec3 position, Vec3 velocity, int[] gradient, int numParticles) {
        if (level.isClientSide) {
            for (int i = 0; i < numParticles; i++) {
                RandomSource rand = level.getRandom();

                Vec3 thisParticleStart = new Vec3(
                        position.x + ((rand.nextGaussian() - 0.5) / 50),
                        position.y + ((rand.nextGaussian() - 0.5) / 50),
                        position.z + ((rand.nextGaussian() - 0.5) / 50)
                );

                int color = ADColorizer.morphBetweenColors(gradient, new Vec3(0.1, 0.1, 0.1), Minecraft.getInstance().getFrameTime(), thisParticleStart);
                level.addParticle(
                        new ConjureParticleOptions(color, true),
                        thisParticleStart.x,
                        thisParticleStart.y,
                        thisParticleStart.z,
                        velocity.x + (rand.nextDouble() - 0.5) / 100,
                        velocity.y + 0.02 * (rand.nextDouble() - 0.5) / 100,
                        velocity.z + (rand.nextDouble() - 0.5) / 100
                );
            }
        }
    }

    public static Vec3 aToBParticleVector(Vec3 a, Vec3 b) {
        // Lifetime of one of these particles is 48-64 ticks for isLight=true
        // See hexcasting.client.particles.ConjureParticle::new
        double speed = 1d/32d;
        return b.subtract(a).multiply(speed, speed, speed);
    }
}
