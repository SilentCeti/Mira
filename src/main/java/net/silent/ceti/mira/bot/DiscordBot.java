package net.silent.ceti.mira.bot;

import arc.Core;
import arc.util.Strings;
import mindustry.Vars;
import mindustry.gen.Groups;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.components.actionrow.ActionRow;
import net.dv8tion.jda.api.components.buttons.Button;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.internal.utils.JDALogger;
import net.silent.ceti.mira.config.DiscordConfig;
import net.silent.ceti.mira.logger.MiraLogger;
import net.silent.ceti.mira.time.RoundTimer;
import net.silent.ceti.mira.time.UptimeTimer;

import java.awt.*;
import java.io.File;
import java.nio.file.Files;
import java.time.Instant;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class DiscordBot {

    private static JDA jda;

    private static TextChannel statusChannel;
    private static Message statusMessage;

    private static ScheduledExecutorService scheduler;

    private static final String MESSAGE_ID_FILE = "config/mira/cache/discord-message-status-id.txt";

    private static UptimeTimer uptimeTimer;

    public static void init() {
        JDALogger.setFallbackLoggerEnabled(false);

        MiraLogger.info("Initializing Discord bot...");

        DiscordConfig.init();

        if (!DiscordConfig.isEnabled()) {
            MiraLogger.warn("[gray]Discord bot is disabled.");
            return;
        }

        if (jda != null) {
            MiraLogger.warn("Discord bot is already running.");
            return;
        }

        uptimeTimer = new UptimeTimer();

        Thread botThread = new Thread(
                DiscordBot::start,
                "Discord"
        );

        botThread.start();
    }

    private static void start() {
        try {
            MiraLogger.info("[cyan]Starting Discord bot...");
            jda = JDABuilder
                    .createDefault(
                            DiscordConfig.getToken()
                    )
                    .addEventListeners(new DiscordButtonListener())
                    .build();

            jda.awaitReady();

            MiraLogger.success("[green]Discord bot connected.");

            statusChannel = jda.getTextChannelById(DiscordConfig.getChannelId());

            if (statusChannel == null) {
                MiraLogger.err("[red]Discord status channel not found.");
                return;
            }

            MiraLogger.success("Discord status channel found: " + statusChannel.getName());

            findStatusMessage();
        } catch (Exception e) {
            MiraLogger.err("[scarlet]Failed to start Discord bot.");
            e.printStackTrace();
        }
    }

    private static void findStatusMessage() {
        File file = new File(MESSAGE_ID_FILE);

        if (!file.exists()) {
            MiraLogger.info("Discord status message ID not found.");

            sendStatusMessage();
            return;
        }

        try {
            String messageId = Files.readString(file.toPath()).trim();

            if (messageId.isEmpty()) {
                MiraLogger.warn("Discord status message ID is empty.");

                sendStatusMessage();
                return;
            }

            MiraLogger.info("Searching for Discord status message...");

            statusChannel.retrieveMessageById(messageId).queue(message -> {
                statusMessage = message;
                MiraLogger.success("[green]Discord status message found.");

                startStatusUpdater();
            }, error -> {
                MiraLogger.warn("[yellow]Discord status message no longer exists.");
                sendStatusMessage();
            });

        } catch (Exception e) {
            MiraLogger.err("[red]Failed to read Discord message ID.");
            e.printStackTrace();
            sendStatusMessage();
        }
    }

    private static void sendStatusMessage() {

        MessageEmbed embed = createServerStatusEmbed(1);

        Button playersButton = Button.primary(
                "server_players",
                "👥 Players"
        );

        statusChannel.sendMessageEmbeds(embed).setComponents(ActionRow.of(playersButton)).queue(message -> {
            statusMessage = message;
            saveMessageId(message.getId());

            MiraLogger.success("[green]Discord status message created.");

            startStatusUpdater();
        }, error -> {
            MiraLogger.err("[red]Failed to send Discord status message.");
            MiraLogger.err(error.getMessage());
        });
    }

    private static void saveMessageId(String id) {
        try {
            File file = new File(MESSAGE_ID_FILE);

            file.getParentFile().mkdirs();
            Files.writeString(
                    file.toPath(),
                    id
            );
        } catch (Exception e) {
            MiraLogger.err("[red]Failed to save Discord message ID.");
            e.printStackTrace();
        }
    }

    private static void startStatusUpdater() {
        if (scheduler != null && !scheduler.isShutdown()) return;

        scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleWithFixedDelay(() -> updateStatusMessage(1), 0, 15, TimeUnit.SECONDS);

        MiraLogger.success("[green]Discord status updater started.");
    }

    private static void updateStatusMessage(int level) {
        Core.app.post(() -> {
            if (statusMessage == null) return;

            MessageEmbed embed = createServerStatusEmbed(level);

            statusMessage.editMessageEmbeds(embed).queue();
        });
    }

    private static MessageEmbed createServerStatusEmbed(int level) {
        int players = Groups.player.size();
        int maxPlayers = Vars.netServer.admins.getPlayerLimit();

        String mapName = Vars.state.map.name();

        String roundTime = RoundTimer.format("%02dd %02dh %02dm %02ds");
        String uptime = uptimeTimer.format("%02dd %02dh %02dm %02ds");

        String tps = Vars.state.serverTps < 0 ? "N/A" : String.format("%.1f", Vars.state.serverTps);

        String serverName = Core.settings.getString("servername");

        if (level == 2) return new EmbedBuilder()
                .setTitle("Server Status - Offline")
                .setDescription(Strings.stripColors(serverName))
                .addField("👥 Players", "- / -", true)
                .addField("🗺️ Map", "-", true)
                .addField("⌚ Round Time", "-", true)
                .addField("Server uptime", "-", true)
                .addField("TPS", "-", true)
                .addField("Updated: ", "<t:" + Instant.now().getEpochSecond() + ":R>", true)
                .setFooter("")
                .setTimestamp(Instant.from(Instant.now()))
                .setColor(Color.RED)
                .build();

        return new EmbedBuilder()
                .setTitle("Server Status - Online")
                .setDescription(Strings.stripColors(serverName))
                .addField("👥 Players", players + " / " + maxPlayers, true)
                .addField("🗺️ Map", mapName, true)
                .addField("⌚ Round Time", roundTime, true)
                .addField("Server uptime", uptime, true)
                .addField("TPS", String.valueOf(tps), true)
                .addField("Updated: ", "<t:" + Instant.now().getEpochSecond() + ":R>", true)
                .setFooter("")
                .setTimestamp(Instant.from(Instant.now()))
                .setColor(Color.green)
                .build();
    }

    public static void shutdown() {
        if (statusMessage != null && jda != null) {
            MessageEmbed embed = createServerStatusEmbed(2);

            statusMessage.editMessageEmbeds(embed).queue(
                    success -> {
                        MiraLogger.info("[gray]Discord status changed to offline.");
                        stopDiscord();
                    },

                    error -> {
                        MiraLogger.warn("[yellow]Failed to update Discord status to offline.");
                        stopDiscord();
                    }
            );
        } else stopDiscord();

    }

    private static void stopDiscord() {
        if (scheduler != null) {
            scheduler.shutdownNow();
            scheduler = null;
        }

        if (jda != null) {
            jda.shutdown();
            jda = null;
        }

        statusChannel = null;
        statusMessage = null;

        MiraLogger.info("[gray]Discord bot stopped.");
    }
}