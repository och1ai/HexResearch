package name.dashkal.minecraft.hexresearch.fabric;

import net.fabricmc.loader.api.FabricLoader;
import name.dashkal.minecraft.hexresearch.HexResearchAbstractions;

import java.nio.file.Path;

public class HexResearchAbstractionsImpl {
    /**
     * This is the actual implementation of {@link HexResearchAbstractions#getConfigDirectory()}.
     */
    public static Path getConfigDirectory() {
        return FabricLoader.getInstance().getConfigDir();
    }
}
