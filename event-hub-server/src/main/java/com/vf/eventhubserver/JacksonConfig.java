package com.vf.eventhubserver;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import java.time.format.DateTimeFormatter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

@Configuration
public class JacksonConfig {

  private static final String DATEFORMAT = "dd-MM-yyyy";
  private static final String DATETIMEFORMAT = DATEFORMAT + " HH:mm:ss";

  @Bean
  protected Jackson2ObjectMapperBuilder jackson2ObjectMapperBuilder() {
    return new Jackson2ObjectMapperBuilder()
        .serializers(new LocalDateSerializer(DateTimeFormatter.ofPattern(DATEFORMAT)))
        .serializers(new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(DATETIMEFORMAT)))
        .deserializers(new LocalDateDeserializer(DateTimeFormatter.ofPattern(DATEFORMAT)))
        .deserializers(new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern(DATETIMEFORMAT)))
        .serializationInclusion(JsonInclude.Include.NON_NULL);
  }
}
