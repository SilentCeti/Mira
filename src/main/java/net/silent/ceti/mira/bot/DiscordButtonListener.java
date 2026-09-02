package net.silent.ceti.mira.bot;

import arc.Core;
import arc.util.Strings;
import mindustry.gen.Groups;
import mindustry.gen.Player;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.time.Instant;

public class DiscordButtonListener extends ListenerAdapter {
    @Override
    public void onButtonInteraction(ButtonInteractionEvent event) {
        if (!event.getComponentId().equals("server_players")) return;

        Core.app.post(() -> {
            MessageEmbed embed = createPlayersEmbed();

            event.replyEmbeds(embed).setEphemeral(true).queue();
        });
    }

    private static MessageEmbed createPlayersEmbed() {
        EmbedBuilder embed = new EmbedBuilder()
                .setTitle("👥 Players online")
                .setTimestamp(Instant.now());

        StringBuilder players = new StringBuilder();

        for (Player player : Groups.player) {
            players.append("• ").append(Strings.stripColors(player.name)).append("\n");
        }

        if (players.isEmpty()) {
            players.append("Server is empty.");
        }

        embed.setDescription(players.toString());

        return embed.build();
    }
}