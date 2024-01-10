package name.dashkal.minecraft.hexresearch.config;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public abstract class HexResearchConfig {
    protected static HexResearchConfig instance;

    /**
     * Returns a handle to the static configuration instance
     */
    public static HexResearchConfig getInstance() {
        return instance;
    }

    /** Returns a handle to the client configuration. Will return <code>null</code> on the server. */
    @Nullable
    public abstract ClientConfig getClientConfig();

    /** Returns a handle to the common configuration. */
    @Nonnull
    public abstract CommonConfig getCommonConfig();

    /** Returns a handle to the server configuration. Will return <code>null</code> until connected to a world. */
    @Nullable
    public abstract ServerConfig getServerConfig();
}
