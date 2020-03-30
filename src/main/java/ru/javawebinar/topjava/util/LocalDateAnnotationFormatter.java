package ru.javawebinar.topjava.util;

import org.springframework.format.AnnotationFormatterFactory;
import org.springframework.format.Formatter;
import org.springframework.format.Parser;
import org.springframework.format.Printer;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

import java.lang.annotation.Annotation;
import java.text.DateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

import static java.util.Arrays.asList;

public class LocalDateAnnotationFormatter implements AnnotationFormatterFactory<DateTimeFormat> {
    @Override
    public Set<Class<?>> getFieldTypes() {
        return new HashSet<>(asList(LocalDate.class, LocalTime.class));
    }

    @Override
    public Printer<?> getPrinter(DateTimeFormat annotation, Class<?> fieldType) {
        if (fieldType.equals(LocalDate.class)) {
            return new LocalDateFormatter();
        } else
            return new LocalTimeFormatter();
    }

    @Override
    public Parser<?> getParser(DateTimeFormat annotation, Class<?> fieldType) {
        if (fieldType.equals(LocalDate.class)) {
            return new LocalDateFormatter();
        } else
            return new LocalTimeFormatter();
    }

}
