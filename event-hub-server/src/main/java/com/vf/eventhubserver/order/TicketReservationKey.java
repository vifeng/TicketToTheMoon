package com.vf.eventhubserver.order;

import com.vf.eventhubserver.show.SessionEvent;
import com.vf.eventhubserver.venue.Seat;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.io.Serializable;

@Embeddable
public class TicketReservationKey implements Serializable {

  @ManyToOne
  @JoinColumn(name = "seat_id")
  private Seat seatId;

  @ManyToOne
  @JoinColumn(name = "session_event_id")
  private SessionEvent sessionEventId;

  public TicketReservationKey() {}

  public TicketReservationKey(Seat seatId, SessionEvent sessionEventId) {
    this.seatId = seatId;
    this.sessionEventId = sessionEventId;
  }

  public Seat getSeatId() {
    return seatId;
  }

  public SessionEvent getSessionEventId() {
    return sessionEventId;
  }

  public void setSeatId(Seat seatId) {
    this.seatId = seatId;
  }

  public void setSessionEventId(SessionEvent sessionEventId) {
    this.sessionEventId = sessionEventId;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((seatId == null) ? 0 : seatId.hashCode());
    result = prime * result + ((sessionEventId == null) ? 0 : sessionEventId.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    TicketReservationKey other = (TicketReservationKey) obj;
    if (seatId == null) {
      if (other.seatId != null) return false;
    } else if (!seatId.equals(other.seatId)) return false;
    if (sessionEventId == null) {
      if (other.sessionEventId != null) return false;
    } else if (!sessionEventId.equals(other.sessionEventId)) return false;
    return true;
  }

  @Override
  public String toString() {
    return "Ticket_ReservationKey [seatId=" + seatId + ", sessionEventId=" + sessionEventId + "]";
  }
}
