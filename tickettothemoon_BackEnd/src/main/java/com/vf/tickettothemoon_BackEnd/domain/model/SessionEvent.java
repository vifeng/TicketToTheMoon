package com.vf.tickettothemoon_BackEnd.domain.model;

import java.io.Serializable;
import java.time.LocalDateTime;
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

    private LocalDateTime dateHourStartSessionEvent;
    private int durationInMinutes;
    private LocalDateTime dateHourEndSessionEvent;
    @ManyToOne
    @JoinColumn(name = "Event_FK")
    private Event event;


    public SessionEvent() {}

    public SessionEvent(Long id, LocalDateTime dateHourStartSessionEvent, int durationInMinutes,
            Event event) {
        setId(id);
        setDateHourStartSessionEvent(dateHourStartSessionEvent);
        setDurationInMinutes(durationInMinutes);
        setEvent(event);
    }

    public SessionEvent(LocalDateTime dateHourStartSessionEvent, int durationInMinutes,
            Event event) {
        setDateHourStartSessionEvent(dateHourStartSessionEvent);
        setDurationInMinutes(durationInMinutes);
        setEvent(event);
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

    public LocalDateTime getDateHourStartSessionEvent() {
        return dateHourStartSessionEvent;
    }

    public void setDateHourStartSessionEvent(LocalDateTime dateHourStartSessionEvent) {
        this.dateHourStartSessionEvent = dateHourStartSessionEvent;
    }

    public LocalDateTime getDateHourEndSessionEvent() {
        return dateHourEndSessionEvent;
    }

    public void setDateHourEndSessionEvent() {
        this.dateHourEndSessionEvent = dateHourStartSessionEvent.plusMinutes(durationInMinutes);
    }

    public int getDurationInMinutes() {
        return durationInMinutes;
    }

    public void setDurationInMinutes(int durationInMinutes) {
        this.durationInMinutes = durationInMinutes;
    }



    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    @Override
    public String toString() {
        return "SessionEvent [id=" + id + ", dateHourStartSessionEvent=" + dateHourStartSessionEvent
                + ", durationInMinutes=" + durationInMinutes + ", dateHourEndSessionEvent="
                + dateHourEndSessionEvent + ", event=" + event + "]";
    }



}
