package com.vf.tickettothemoon_BackEnd.domain.model;

import java.io.Serializable;
import java.time.LocalDate;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

/**
 * An event is a show, a concert, a movie, a play, etc. It has a name, a start date, an end date and
 * is composed of sessions. It has one configuration hall.
 */
@Entity
public class Event implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private LocalDate dateStart;
    private LocalDate dateEnd;
    private String closedDay;

    @ManyToOne
    @JoinColumn(name = "ConfigurationHall_FK")
    private ConfigurationHall configurationHall;

    public Event() {}

    public Event(Long id, String name, LocalDate dateStart, LocalDate dateEnd, String closedDay,
            ConfigurationHall configurationHall) {
        setId(id);
        setName(name);
        setDateStart(dateStart);
        setDateEnd(dateEnd);
        setClosedDay(closedDay);
        setConfigurationHall(configurationHall);
    }


    public Event(String name, LocalDate dateStart, LocalDate dateEnd, String closedDay,
            ConfigurationHall configurationHall) {
        setName(name);
        setDateStart(dateStart);
        setDateEnd(dateEnd);
        setClosedDay(closedDay);
        setConfigurationHall(configurationHall);
    }

    private void setId(Long id) {
        this.id = id;
    }

    private void setDateEnd(LocalDate dateEnd) {
        this.dateEnd = dateEnd;
    }

    private void setDateStart(LocalDate dateStart) {
        this.dateStart = dateStart;
    }

    private void setName(String name) {
        this.name = name;
    }

    public void setClosedDay(String closedDay) {
        this.closedDay = closedDay;
    }

    private void setConfigurationHall(ConfigurationHall configurationHall) {
        this.configurationHall = configurationHall;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDate getDateStart() {
        return dateStart;
    }

    public LocalDate getDateEnd() {
        return dateEnd;
    }

    public String getClosedDay() {
        return closedDay;
    }

    public ConfigurationHall getConfigurationHall() {
        return configurationHall;
    }

    @Override
    public String toString() {
        return "Event: [" + name + " DateStart: " + dateStart + " DateEnd: " + dateEnd
                + " ClosedDay: " + closedDay + "configurationHall : " + configurationHall + "]";
    }

}
