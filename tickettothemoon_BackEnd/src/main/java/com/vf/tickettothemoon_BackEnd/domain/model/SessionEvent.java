package com.vf.tickettothemoon_BackEnd.domain.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

/**
 * A session event is a session of an event. It has a date, an eventHour and a duration. It is part
 * of an event.
 */
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class SessionEvent implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    // TOCHECK: FetchType.LAZY or EAGER?
    /// manytomany relationship with composite key and attribute bidirectionnal using
    /// Ticket_Reservation and Ticket_ReservationKey
    @OneToMany(mappedBy = "sessionEvent", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    Set<Ticket_Reservation> ticket_Reservations = new HashSet<>();


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

    // set up manytomany with composite key relationship
    public Set<Ticket_Reservation> getTicket_Reservations() {
        return ticket_Reservations;
    }

    public void setTicket_Reservations(Set<Ticket_Reservation> ticket_Reservations) {
        this.ticket_Reservations = ticket_Reservations;
    }


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        SessionEvent other = (SessionEvent) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "SessionEvent [id=" + id + ", dateAndTimeStartSessionEvent="
                + dateAndTimeStartSessionEvent + ", durationInMinutes=" + durationInMinutes
                + ", dateAndTimeEndSessionEvent=" + dateAndTimeEndSessionEvent + ", event=" + event
                + ", configurationHall=" + configurationHall + ", ticket_Reservations="
                + ticket_Reservations + "]";
    }

}
