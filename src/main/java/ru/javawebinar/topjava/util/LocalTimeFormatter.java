package ru.javawebinar.topjava.util;

import org.springframework.format.Formatter;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalTime;

public class LocalTimeFormatter implements Formatter<LocalTime> {

    public LocalTimeFormatter() {
    }

    @Override
    public LocalTime parse(String text, Locale locale) {
        if (!text.equals("") && text.length() != 0) {
            return parseLocalTime(text);
        }
        return null;
    }

    @Override
    public String print(LocalTime dateTime, Locale locale) {
        return dateTime.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
    }

}
