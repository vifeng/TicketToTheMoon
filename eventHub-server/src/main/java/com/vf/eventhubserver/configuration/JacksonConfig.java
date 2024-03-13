package com.vf.eventhubserver.configuration;

import java.time.format.DateTimeFormatter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

@Configuration
public class JacksonConfig {

    private static final String DATEFORMAT = "dd-MM-yyyy";
    private static final String DATETIMEFORMAT = DATEFORMAT + " HH:mm:ss";

    @Bean

    protected Jackson2ObjectMapperBuilder jackson2ObjectMapperBuilder() {
        return new Jackson2ObjectMapperBuilder()
                .serializers(new LocalDateSerializer(DateTimeFormatter.ofPattern(DATEFORMAT)))
                .serializers(
                        new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(DATETIMEFORMAT)))
                .serializationInclusion(JsonInclude.Include.NON_NULL);
    }

}
