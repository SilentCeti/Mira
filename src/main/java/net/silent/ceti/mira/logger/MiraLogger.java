package net.silent.ceti.mira.logger;

import net.silent.ceti.mira.string.MindustryColorParser;
import net.silent.ceti.mira.time.Time;
import net.silent.ceti.mira.time.UptimeTimer;

public class MiraLogger {
    private static final String LOG_PREFIX = "[scarlet][[red]~[scarlet]Mira[red]~[scarlet]][r]";

    private static final Time time = new Time();

    private enum LogLevel {

        INFO("[sky]", "INFO"),
        SUCCESS("[green]", "SUCC"),
        WARN("[orange]", "WARN"),
        ERROR("[scarlet]", "ERRO"),
        DEBUG("[cyan]", "DEBG"),
        DATA("[olive]", "DATA"),
        NETWORK("[slate]", "NET");

        private final String color;
        private final String name;

        LogLevel(String color, String name) {
            this.color = color;
            this.name = name;
        }
    }

    public static void info(String text) {
        log(LogLevel.INFO, text);
    }

    public static void success(String text) {
        log(LogLevel.SUCCESS, text);
    }

    public static void warn(String text) {
        log(LogLevel.WARN, text);
    }

    public static void err(String text) {
        log(LogLevel.ERROR, text);
    }

    public static void debug(String text) {
        log(LogLevel.DEBUG, text);
    }

    public static void data(String text) {
        log(LogLevel.DATA, text);
    }

    public static void net(String text) {
        log(LogLevel.NETWORK, text);
    }

    private static void log(LogLevel level, String text) {
        String result = "[gray][b][" + time.format("%02d:%02d:%02d") + "][r] " + LOG_PREFIX + "[r] " + level.color + "[" + level.name + "][r] " + text + "[r]";

        System.out.print("\r\u001B[2K");
        System.out.println(MindustryColorParser.toAnsi(result));
    }
}
