package name.dashkal.minecraft.hexresearch.forge;

import name.dashkal.minecraft.hexresearch.HexResearchAbstractions;
import net.minecraftforge.fml.loading.FMLPaths;

import java.nio.file.Path;

public class HexResearchAbstractionsImpl {
    /**
     * This is the actual implementation of {@link HexResearchAbstractions#getConfigDirectory()}.
     */
    public static Path getConfigDirectory() {
        return FMLPaths.CONFIGDIR.get();
    }
}
