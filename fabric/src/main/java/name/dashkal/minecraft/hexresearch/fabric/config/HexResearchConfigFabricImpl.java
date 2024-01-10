package name.dashkal.minecraft.hexresearch.fabric.config;

import com.google.gson.Gson;
import com.google.gson.stream.JsonWriter;
import dev.architectury.platform.Platform;
import name.dashkal.minecraft.hexresearch.HexResearch;
import name.dashkal.minecraft.hexresearch.config.ClientConfig;
import name.dashkal.minecraft.hexresearch.config.CommonConfig;
import name.dashkal.minecraft.hexresearch.config.HexResearchConfig;
import name.dashkal.minecraft.hexresearch.config.ServerConfig;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

public class HexResearchConfigFabricImpl extends HexResearchConfig {
    public static void init() {
        HexResearchConfigFabricImpl.instance = new HexResearchConfigFabricImpl();
        HexResearchConfig.instance = HexResearchConfigFabricImpl.instance;
    }

    protected static HexResearchConfigFabricImpl instance;

    public static HexResearchConfigFabricImpl getInstance() {
        return instance;
    }

    private final Gson gson;

    private final AtomicReference<ServerConfig> serverConfig;

    public HexResearchConfigFabricImpl() {
        gson = new Gson();
        serverConfig = new AtomicReference<>(ServerConfig.getDefault());
    }

    @Nullable
    @Override
    public ClientConfig getClientConfig() {
        return ClientConfig.getDefault();
    }

    @Nonnull
    @Override
    public CommonConfig getCommonConfig() {
        return CommonConfig.getDefault();
    }

    @Nullable
    @Override
    public ServerConfig getServerConfig() {
        return serverConfig.get();
    }

    public void setServerConfig(ServerConfig serverConfig) {
        this.serverConfig.set(serverConfig);
    }

    public void loadServerConfig() {
        Path configFolder = Platform.getConfigFolder();
        Path serverConfigFile = configFolder.resolve(String.format("%s-server.json", HexResearch.MOD_ID));

        Optional<ServerConfig> serverConfig = load(serverConfigFile, ServerConfig.class);
        if (serverConfig.isEmpty()) {
            save(serverConfigFile, ServerConfig.getDefault(), ServerConfig.class);
        }

        this.serverConfig.set(serverConfig.orElse(ServerConfig.getDefault()));
    }

    private <T> Optional<T> load(Path path, Class<T> cls) {
        try {
            File file = path.toFile();
            if (file.exists()) {
                try (FileReader reader = new FileReader(file)) {
                    return Optional.of(gson.fromJson(reader, cls));
                }
            } else {
                return Optional.empty();
            }
        } catch (Exception e) {
            throw new RuntimeException("Unable to load configuration from " + path, e);
        }
    }

    private <T> void save(Path path, T t, Class<T> cls) {
        try {
            try (FileWriter writer = new FileWriter(path.toFile(), false)) {
                JsonWriter jsonWriter = gson.newJsonWriter(writer);
                jsonWriter.setIndent("  ");
                gson.toJson(t, cls, jsonWriter);
            }
        } catch (IOException e) {
            throw new RuntimeException("Unable to save configuration to " + path, e);
        }
    }
}
