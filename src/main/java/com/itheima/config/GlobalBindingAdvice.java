package com.itheima.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.Formatter;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.Locale;

@Configuration
public class GlobalBindingAdvice implements WebMvcConfigurer {
    @Override
    public void addFormatters(FormatterRegistry registry) {
        // 空字符串 → null（Short）
        registry.addFormatterForFieldType(Short.class, new Formatter<Short>() {
            @Override
            public Short parse(String text, Locale locale) throws ParseException {
                if (text == null || text.trim().isEmpty()) {
                    return null;
                }
                return Short.valueOf(text);
            }

            @Override
            public String print(Short object, Locale locale) {
                return object != null ? object.toString() : "";
            }
        });

        // 空字符串 → null（LocalDate）
        registry.addFormatterForFieldType(LocalDate.class, new Formatter<LocalDate>() {
            @Override
            public LocalDate parse(String text, Locale locale) throws ParseException {
                if (text == null || text.trim().isEmpty()) {
                    return null;
                }
                return LocalDate.parse(text);
            }

            @Override
            public String print(LocalDate object, Locale locale) {
                return object != null ? object.toString() : "";
            }
        });
    }
}