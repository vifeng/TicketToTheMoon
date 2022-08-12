package com.vf.tickettothemoon.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Typesafe custom configuration.
 * <p>
 * Offers contextual help and "code completion" as users are working with application.properties.
 *
 * @author Antoine Rey
 */
@ConfigurationProperties(prefix = "tickettothemoon")
public class TickettothemoonProperties {
      /**
     * Relational database supported by SpringBoot : h2, MySQL 
     */
    private String database;

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }
}
