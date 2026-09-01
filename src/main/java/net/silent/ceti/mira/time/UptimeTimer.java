package net.silent.ceti.mira.time;

public class UptimeTimer {
    private long startTime = System.nanoTime();

    public void uptimeTimer() {
        startTime = System.nanoTime();
    }

    public long getMillis() {
        return (System.nanoTime() - startTime) / 1_000_000L;
    }

    public long getSeconds() {
        return (System.nanoTime() - startTime) / 1_000_000_000L;
    }

    public long getMinutes() {
        return getSeconds() / 60;
    }

    public long getHours() {
        return getMinutes() / 60;
    }

    public long getDays() {
        return getHours() / 24;
    }

    /** Returns time util server started in format (days, hours, minutes, seconds). */
    public String format(String format) {
        long totalSeconds = getSeconds();

        long days = totalSeconds / 86400;
        long hours = (totalSeconds / 3600) % 24;
        long minutes = (totalSeconds % 3600) / 60;
        long seconds = totalSeconds % 60;

        return String.format(format, days, hours, minutes, seconds);
    }
}