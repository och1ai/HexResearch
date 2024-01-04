package name.dashkal.minecraft.hexresearch.registry;

import name.dashkal.minecraft.hexresearch.mindharm.MindHarmMechanic;
import name.dashkal.minecraft.hexresearch.mindharm.*;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class MindHarms {
    private static final Map<ResourceLocation, MindHarmMechanic> mechanicMap = new ConcurrentHashMap<>();;

    public static MindHarmMechanic DERANK = register(new MindHarmDerankProfession());
    // NYI - public static MindHarmMechanic FORGET_GOSSIP = register(new MindHarmForgetGossip());
    public static MindHarmMechanic FORGET_TRADE = register(new MindHarmForgetTrade());
    public static MindHarmMechanic BRAINSWEEP = register(new MindHarmBrainsweep());
    public static MindHarmMechanic KILL = register(new MindHarmKill());

    public static void init() {
        // Calling this will force static initialization if it hasn't occurred yet, registering the harms.
    }

    public static MindHarmMechanic register(@Nonnull MindHarmMechanic mechanic) {
        if (mechanicMap.containsKey(mechanic.getId())) {
            throw new IllegalArgumentException(mechanic.getId() + " was already registered");
        }
        mechanicMap.put(mechanic.getId(), mechanic);
        return mechanic;
    }

    @Nonnull
    public static Optional<MindHarmMechanic> get(@Nonnull ResourceLocation id) {
        return Optional.ofNullable(mechanicMap.get(id));
    }

    @Nonnull
    public static Map<ResourceLocation, MindHarmMechanic> getAll() {
        return new HashMap<>(mechanicMap);
    }
}
