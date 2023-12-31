package name.dashkal.minecraft.hexresearch.forge;

import name.dashkal.minecraft.hexresearch.xplat.XPlatAPI;
import net.minecraftforge.fml.loading.FMLPaths;

import java.nio.file.Path;

public class HexResearchAbstractionsImpl {
    /**
     * This is the actual implementation of {@link XPlatAPI}.
     */
    public static Path getConfigDirectory() {
        return FMLPaths.CONFIGDIR.get();
    }
}
