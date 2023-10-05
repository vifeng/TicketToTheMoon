package com.vf.tickettothemoon_BackEnd.domain.model;

import java.io.Serializable;
import jakarta.persistence.Embeddable;

@Embeddable
public class Ticket_ReservationId implements Serializable {

    private final Long id;
    private final Long seatId;
    private final Long sessionId;

    private Ticket_ReservationId() {
        this.id = null;
        this.seatId = null;
        this.sessionId = null;
    }

    public Ticket_ReservationId(Long id, Long seatId, Long sessionId) {
        this.id = id;
        this.seatId = seatId;
        this.sessionId = sessionId;
    }

    // the fields within the embeddable class should be made immutable (i.e., no setters, or the
    // setters should be protected/private) to ensure that the key remains consistent throughout the
    // entity's lifecycle.

    public Long getId() {
        return id;
    }

    public Long getSeatId() {
        return seatId;
    }

    public Long getSessionId() {
        return sessionId;
    }
}
