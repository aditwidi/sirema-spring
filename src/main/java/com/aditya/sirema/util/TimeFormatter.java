package com.aditya.sirema.util;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class TimeFormatter {

    public static String format(LocalDateTime timestamp) {
        LocalDateTime now = LocalDateTime.now();

        long years = ChronoUnit.YEARS.between(timestamp, now);
        if (years > 0) {
            return years + " year" + (years > 1 ? "s" : "") + " ago";
        }

        long months = ChronoUnit.MONTHS.between(timestamp, now);
        if (months > 0) {
            return months + " month" + (months > 1 ? "s" : "") + " ago";
        }

        long days = ChronoUnit.DAYS.between(timestamp, now);
        if (days > 0) {
            return days + " day" + (days > 1 ? "s" : "") + " ago";
        }

        long hours = ChronoUnit.HOURS.between(timestamp, now);
        if (hours > 0) {
            return hours + " hour" + (hours > 1 ? "s" : "") + " ago";
        }

        long minutes = ChronoUnit.MINUTES.between(timestamp, now);
        if (minutes > 0) {
            return minutes + " minute" + (minutes > 1 ? "s" : "") + " ago";
        }

        long seconds = ChronoUnit.SECONDS.between(timestamp, now);
        return seconds + " second" + (seconds > 1 ? "s" : "") + " ago";
    }
}

