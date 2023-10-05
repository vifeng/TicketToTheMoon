package com.vf.tickettothemoon_BackEnd.domain.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
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

    // relationships
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH},
            optional = false)
    @JoinColumn(name = "Event_FK")
    private Event event;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH},
            optional = false)
    @JoinColumn(name = "ConfigurationHall_FK")
    private ConfigurationHall configurationHall;

    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinTable(name = "Ticket_Reservation", joinColumns = @JoinColumn(name = "SessionEvent_FK"),
            inverseJoinColumns = @JoinColumn(name = "Seat_FK"))
    @JsonIgnoreProperties("sessionEvents")
    private List<Seat> seats = new ArrayList<>();

    public SessionEvent() {}

    public SessionEvent(Long id, LocalDateTime dateAndTimeStartSessionEvent, int durationInMinutes,
            Event event, ConfigurationHall configurationHall, List<Seat> seats) {
        setId(id);
        setDateAndTimeStartSessionEvent(dateAndTimeStartSessionEvent);
        setDurationInMinutes(durationInMinutes);
        setEvent(event);
        setConfigurationHall(configurationHall);
        setSeats(seats);
    }

    public SessionEvent(LocalDateTime dateAndTimeStartSessionEvent, int durationInMinutes,
            Event event, ConfigurationHall configurationHall, List<Seat> seats) {
        setDateAndTimeStartSessionEvent(dateAndTimeStartSessionEvent);
        setDurationInMinutes(durationInMinutes);
        setEvent(event);
        setConfigurationHall(configurationHall);
        setSeats(seats);
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

    ///////////////////
    // RELATIONSHIPS //
    ///////////////////
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
    // set up many to many bidirectionnal relationship owner side

    public void addSeats(Seat seat) {
        seats.add(seat);
        seat.getSessionEvents().add(this);
    }


    public void removeSeats(Seat seat) {
        if (seats != null)
            seats.remove(seat);
        seat.getSessionEvents().remove(this);
    }

    public List<Seat> getSeats() {
        return seats;
    }

    public void setSeats(List<Seat> seats) {
        if (seats != null)
            this.seats = seats;
    }

    @Override
    public String toString() {
        return "SessionEvent [id=" + id + ", dateAndTimeStartSessionEvent="
                + dateAndTimeStartSessionEvent + ", durationInMinutes=" + durationInMinutes
                + ", dateAndTimeEndSessionEvent=" + dateAndTimeEndSessionEvent + ", event=" + event
                + ", configurationHall=" + configurationHall + ", seats=" + seats + "]";
    }



}
