package net.silent.ceti.mira.time;

import arc.Events;
import mindustry.game.EventType;

import java.time.LocalDateTime;

public class RoundTimer {
    private static long startTime;

    public static void init() {
        Events.on(EventType.WorldLoadEvent.class, (event) -> {
            startTime = System.nanoTime();
        });
    }

    public static long getMillis() {
        return (System.nanoTime() - startTime) / 1_000_000L;
    }

    public static long getSeconds() {
        return (System.nanoTime() - startTime) / 1_000_000_000L;
    }

    public static long getMinutes() {
        return getSeconds() / 60;
    }

    public static long getHours() {
        return getMinutes() / 60;
    }

    /** Returns time util round started in format (hours, minutes, seconds). */
    public String getTime(String format) {
        long totalSeconds = getSeconds();

        long hours = (totalSeconds / 3600) % 24;
        long minutes = (totalSeconds % 3600) / 60;
        long seconds = totalSeconds % 60;
        return String.format(format, hours, minutes, seconds);
    }

    /** Returns time util round started in format (hours, minutes, seconds). */
    public static String format(String format) {
        long totalSeconds = getSeconds();

        long days = totalSeconds / 86400;
        long hours = (totalSeconds / 3600) % 24;
        long minutes = (totalSeconds % 3600) / 60;
        long seconds = totalSeconds % 60;

        return String.format(format, days, hours, minutes, seconds);
    }
}