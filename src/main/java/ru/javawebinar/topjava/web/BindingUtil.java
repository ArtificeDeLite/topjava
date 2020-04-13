package ru.javawebinar.topjava.web;

import org.springframework.validation.BindingResult;

import java.util.StringJoiner;

public class BindingUtil {

    public static String bindingResultCheck(BindingResult result) {
        if (result.hasErrors()) {
            StringJoiner joiner = new StringJoiner("<br>");
            result.getFieldErrors().forEach(
                    fe -> joiner.add(String.format("[%s] %s", fe.getField(), fe.getDefaultMessage()))
            );
            return joiner.toString();
        }
        return null;
    }
}
