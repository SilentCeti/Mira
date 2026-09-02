package net.silent.ceti.mira;

import arc.ApplicationListener;
import arc.Core;
import mindustry.mod.Plugin;
import net.silent.ceti.mira.bot.DiscordBot;
import net.silent.ceti.mira.logger.MiraLogger;
import net.silent.ceti.mira.time.RoundTimer;

public class Main extends Plugin {
    @Override
    public void init() {
        MiraLogger.info("Mira plugin loaded");

        DiscordBot.init();

        RoundTimer.init();

        Core.app.addListener(new ApplicationListener() {
            @Override
            public void dispose() {
                DiscordBot.shutdown();
            }
        });
    }
}
