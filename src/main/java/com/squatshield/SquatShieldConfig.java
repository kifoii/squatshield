package com.squatshield;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

import net.fabricmc.loader.api.FabricLoader;

public final class SquatShieldConfig {
    private static final Path FILE = FabricLoader.getInstance().getConfigDir().resolve("squatshield.properties");

    public static boolean enabled = true;

    private SquatShieldConfig() {}

    public static void load() {
        if (!Files.exists(FILE)) return;
        Properties p = new Properties();
        try (InputStream in = Files.newInputStream(FILE)) {
            p.load(in);
            enabled = Boolean.parseBoolean(p.getProperty("enabled", "true"));
        } catch (IOException ignored) {
        }
    }

    public static void save() {
        Properties p = new Properties();
        p.setProperty("enabled", Boolean.toString(enabled));
        try {
            Files.createDirectories(FILE.getParent());
            try (OutputStream out = Files.newOutputStream(FILE)) {
                p.store(out, "SquatShield configuration");
            }
        } catch (IOException ignored) {
        }
    }

    public static void reset() {
        enabled = true;
        save();
    }
}
