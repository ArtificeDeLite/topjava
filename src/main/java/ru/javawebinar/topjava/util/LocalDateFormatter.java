package ru.javawebinar.topjava.util;

import org.springframework.format.Formatter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalDate;

public class LocalDateFormatter implements Formatter<LocalDate> {

    public LocalDateFormatter() {
    }

    @Override
    public LocalDate parse(String text, Locale locale) {
        if (!text.equals("") && text.length() != 0) {
            return parseLocalDate(text);
        }
        return null;
    }

    @Override
    public String print(LocalDate dateTime, Locale locale) {
        return dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }
}
