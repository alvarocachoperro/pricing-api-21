package es.ecommerce.demo.infra.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.time.*;
import java.time.format.DateTimeFormatter;

@Configuration
public class OffsetDateTimeConverterConfig implements WebMvcConfigurer {

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(String.class, OffsetDateTime.class, source -> {
            if (source == null || source.isBlank()) return null;
            // Try parse with offset first
            try {
                return OffsetDateTime.parse(source, DateTimeFormatter.ISO_DATE_TIME);
            } catch (Exception ignore) {
            }
            // Fallback: parse as LocalDateTime without offset and assume UTC
            LocalDateTime ldt = LocalDateTime.parse(source, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
            return ldt.atOffset(ZoneOffset.UTC);
        });
    }
}
