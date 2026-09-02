package net.silent.ceti.mira.config;

import arc.files.Fi;
import arc.util.serialization.Json;
import arc.util.serialization.JsonValue;
import net.silent.ceti.mira.logger.MiraLogger;

public class DiscordConfig {
    private static final String CONFIG_FILE = "config/mira/discord.json";

    private static boolean enabled;
    private static String token;
    private static String channelId;

    public static void init() {
        Fi file = Fi.get(CONFIG_FILE);

        MiraLogger.info("Loading Discord config...");

        if (!file.exists()) {
            createDefaultConfig(file);
        }

        loadConfig(file);
    }

    private static void createDefaultConfig(Fi file) {
        MiraLogger.warn("Discord config not found.");

        file.parent().mkdirs();

        JsonValue json = new JsonValue(JsonValue.ValueType.object);

        json.addChild("enabled", new JsonValue(false));
        json.addChild("token", new JsonValue(""));
        json.addChild("channelId", new JsonValue(""));

        file.writeString(json.toString(), false);

        MiraLogger.success("Discord config created: " + CONFIG_FILE);
    }

    private static void loadConfig(Fi file) {
        JsonValue json = new Json().fromJson(null, file);

        enabled = json.getBoolean("enabled", false);
        token = json.getString("token", "");
        channelId = json.getString("channelId", "");

        MiraLogger.success("Discord config loaded.");
    }

    public static boolean isEnabled() {
        return enabled;
    }

    public static String getToken() {
        return token;
    }

    public static String getChannelId() {
        return channelId;
    }
}