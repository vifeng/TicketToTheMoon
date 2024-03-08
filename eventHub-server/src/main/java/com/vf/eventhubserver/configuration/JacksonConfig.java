package com.vf.eventhubserver.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@Configuration
public class JacksonConfig {

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();

        // Configuration pour ignorer les propriétés inconnues dans la désérialisation
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        // Configurez l'inclusion des valeurs NON NULL. permet de globaliser l'annotation suivante à
        // toutes les entités
        // @JsonInclude(JsonInclude.Include.NON_NULL) // Pour ignorer les champs null lors de la
        // sérialisation
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        // Configuration pour formater la sortie JSON de manière lisible
        objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);

        // Configuration pour LocalDate, LocalTime, Instant, etc.
        objectMapper.registerModule(new JavaTimeModule());
        // Configuration pour les Optional
        objectMapper.registerModule(new Jdk8Module());

        return objectMapper;
    }
}
