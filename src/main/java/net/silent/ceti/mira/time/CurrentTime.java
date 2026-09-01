package net.silent.ceti.mira.time;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CurrentTime {
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("MM-dd-yyyy");

    private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("HH:mm:ss");

    public String getDate() {
        return LocalDateTime.now().format(DATE_FORMAT);
    }

    public String getTime() {
        return LocalDateTime.now().format(TIME_FORMAT);
    }
}
