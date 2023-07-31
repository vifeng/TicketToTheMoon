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

    private LocalDateTime date;
    private int eventHour;
    private int duration;

    @ManyToOne
    @JoinColumn(name = "Event_FK")
    private Event event;

    public SessionEvent() {}

    public SessionEvent(Long id, LocalDateTime date, int eventHour, int duration, Event event) {
        setId(id);
        setDate(date);
        setHour(eventHour);
        setDuration(duration);
        setEvent(event);
    }

    public SessionEvent(LocalDateTime date, int eventHour, int duration, Event event) {
        setDate(date);
        setHour(eventHour);
        setDuration(duration);
        setEvent(event);
    }

    private void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public int getHour() {
        return eventHour;
    }

    public void setHour(int eventHour) {
        this.eventHour = eventHour;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    @Override
    public String toString() {
        return "SessionEvent [id=" + id + ", date=" + date + ", eventHour=" + eventHour
                + ", duration=" + duration + ", event=" + event + "]";
    }



}
