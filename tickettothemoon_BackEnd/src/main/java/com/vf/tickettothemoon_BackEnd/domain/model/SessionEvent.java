package com.vf.tickettothemoon_BackEnd.domain.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

/**
 * A session event is a session of an event. It has a date, an eventHour and a duration. It is part
 * of an event.
 */
@Entity
public class SessionEvent implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    private LocalDateTime dateAndTimeStartSessionEvent;
    private int durationInMinutes;
    private LocalDateTime dateAndTimeEndSessionEvent;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH},
            optional = false)
    @JoinColumn(name = "Event_FK")
    private Event event;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH},
            optional = false)
    @JoinColumn(name = "ConfigurationHall_FK")
    private ConfigurationHall configurationHall;


    public SessionEvent() {}

    public SessionEvent(Long id, LocalDateTime dateAndTimeStartSessionEvent, int durationInMinutes,
            Event event, ConfigurationHall configurationHall) {
        setId(id);
        setDateAndTimeStartSessionEvent(dateAndTimeStartSessionEvent);
        setDurationInMinutes(durationInMinutes);
        setEvent(event);
        setConfigurationHall(configurationHall);
    }

    public SessionEvent(LocalDateTime dateAndTimeStartSessionEvent, int durationInMinutes,
            Event event, ConfigurationHall configurationHall) {
        setDateAndTimeStartSessionEvent(dateAndTimeStartSessionEvent);
        setDurationInMinutes(durationInMinutes);
        setEvent(event);
        setConfigurationHall(configurationHall);
    }

    //////////////
    // METHODS
    ////////////
    // TODO_LOW: création de sessions
    // - créer automatiquement les sessions en fonctions des dates de l'event et des jours
    // de fermeture du lieu.
    // - supression automatique des sessions en fonctions des dates de début et fins.

    /////////////////////
    // GETTERS & SETTERS
    /////////////////////

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDateAndTimeStartSessionEvent() {
        return dateAndTimeStartSessionEvent;
    }

    public void setDateAndTimeStartSessionEvent(LocalDateTime dateAndTimeStartSessionEvent) {
        this.dateAndTimeStartSessionEvent = dateAndTimeStartSessionEvent;
    }

    public LocalDateTime getDateAndTimeEndSessionEvent() {
        return dateAndTimeEndSessionEvent;
    }

    public void setDateAndTimeEndSessionEvent() {
        this.dateAndTimeEndSessionEvent =
                dateAndTimeStartSessionEvent.plusMinutes(durationInMinutes);
    }

    public int getDurationInMinutes() {
        return durationInMinutes;
    }

    public void setDurationInMinutes(int durationInMinutes) {
        this.durationInMinutes = durationInMinutes;
    }

    // relationships

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }


    public ConfigurationHall getConfigurationHall() {
        return configurationHall;
    }

    public void setConfigurationHall(ConfigurationHall configurationHall) {
        this.configurationHall = configurationHall;
    }

    @Override
    public String toString() {
        return "SessionEvent [id=" + id + ", dateAndTimeStartSessionEvent="
                + dateAndTimeStartSessionEvent + ", durationInMinutes=" + durationInMinutes
                + ", dateAndTimeEndSessionEvent=" + dateAndTimeEndSessionEvent + ", event=" + event
                + "]";
    }



}
