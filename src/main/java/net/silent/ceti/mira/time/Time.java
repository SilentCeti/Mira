package net.silent.ceti.mira.time;

public class Time {
    private long time = System.currentTimeMillis();

    public void uptimeTimer() {
        time = System.currentTimeMillis();
    }

    public long getMillis() {
        return (System.currentTimeMillis() - time);
    }

    public long getSeconds() {
        return (System.currentTimeMillis() - time) / 1_000L;
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

    /** Returns time util server started in format (hours, minutes, seconds). */
    public String format(String format) {
        long totalSeconds = getSeconds();

        long hours = (totalSeconds / 3600) % 24;
        long minutes = (totalSeconds % 3600) / 60;
        long seconds = totalSeconds % 60;

        return String.format(format, hours, minutes, seconds);
    }
}
