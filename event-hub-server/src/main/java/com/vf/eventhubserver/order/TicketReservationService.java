package com.vf.eventhubserver.order;

import com.vf.eventhubserver.exception.CreateException;
import com.vf.eventhubserver.exception.DuplicateKeyException;
import com.vf.eventhubserver.exception.FinderException;
import com.vf.eventhubserver.exception.NullException;
import com.vf.eventhubserver.exception.UpdateException;
import com.vf.eventhubserver.show.SessionEvent;
import com.vf.eventhubserver.show.SessionEventRepository;
import com.vf.eventhubserver.venue.Seat;
import com.vf.eventhubserver.venue.SeatRepository;
import com.vf.eventhubserver.venue.SeatStatus;
import com.vf.eventhubserver.venue.SeatStatusRepository;
import java.util.Collection;
import java.util.Set;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TicketReservationService {
  private TicketReservationRepository ticketReservationRepository;
  private final TicketReservationMapper ticketReservationMapper;
  private SeatRepository seatRepository;
  private SessionEventRepository sessionEventRepository;
  private SeatStatusRepository seatStatusRepository;
  static final String NOTFOUNDMSG = "} not found";
  static final String TRNULLMSG = "TicketReservation is null";

  public TicketReservationService(
      TicketReservationRepository ticketReservationRepository,
      TicketReservationMapper ticketReservationMapper,
      SeatRepository seatRepository,
      SessionEventRepository sessionEventRepository,
      SeatStatusRepository seatStatusRepository) {
    this.ticketReservationRepository = ticketReservationRepository;
    this.ticketReservationMapper = ticketReservationMapper;
    this.seatRepository = seatRepository;
    this.sessionEventRepository = sessionEventRepository;
    this.seatStatusRepository = seatStatusRepository;
  }

  public Set<TicketReservationDTO> findAll() throws FinderException {
    Iterable<TicketReservation> ticketReservations = ticketReservationRepository.findAll();
    int size = ((Collection<TicketReservation>) ticketReservations).size();
    if (size == 0) throw new FinderException("No ticketReservations in the database");
    return ticketReservationMapper.toDTOs(ticketReservations);
  }

  public TicketReservationDTO findById(Long sessioneventId, Long seatId) throws FinderException {
    Seat seat =
        seatRepository
            .findById(seatId)
            .orElseThrow(() -> new FinderException("Seat with id " + seatId + NOTFOUNDMSG));
    SessionEvent sessionEvent =
        sessionEventRepository
            .findById(sessioneventId)
            .orElseThrow(
                () -> new FinderException("SessionEvent with id " + sessioneventId + NOTFOUNDMSG));
    if (seat == null || sessionEvent == null) {
      throw new FinderException("TicketReservation not found");
    }
    TicketReservationKey id = new TicketReservationKey(seat, sessionEvent);
    TicketReservation ticketReservation =
        ticketReservationRepository
            .findById(id)
            .orElseThrow(
                () -> new FinderException("Ticket_Reservation with id " + id + NOTFOUNDMSG));
    if (ticketReservation == null) {
      throw new NullException(TRNULLMSG);
    }
    return ticketReservationMapper.toDTO(ticketReservation);
  }

  public TicketReservationDTO createTicketReservation(TicketReservationDTO ticketReservationDTO)
      throws IllegalArgumentException, CreateException, DuplicateKeyException {
    try {
      TicketReservation ticketReservation = ticketReservationMapper.toEntity(ticketReservationDTO);
      ticketReservationRepository
          .findById(ticketReservation.getId())
          .ifPresent(
              ticketReservationKey -> {
                throw new DuplicateKeyException(
                    "Ticket_Reservation not created, The following id already exists in the database: "
                        + ticketReservationDTO.ticketReservationKey());
              });
      ticketReservationRepository.save(ticketReservation);
      return ticketReservationMapper.toDTO(ticketReservation);
    } catch (DuplicateKeyException e) {
      throw new DuplicateKeyException(e.getMessage());
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException("Ticket_Reservation not created : " + e.getMessage(), e);
    } catch (Exception e) {
      throw new CreateException("Ticket_Reservation not created : " + e.getMessage(), e);
    }
  }

  public void deleteById(TicketReservationKey id) {
    if (!ticketReservationRepository.existsById(id))
      throw new IllegalArgumentException("Ticket_Reservation with id " + id + NOTFOUNDMSG);
    ticketReservationRepository.deleteById(id);
  }

  public void changeSeatsStatus(TicketReservation ticketReservation, String status)
      throws UpdateException {
    try {
      Seat seat = ticketReservation.getId().getSeatId();
      SeatStatus seatStatusAvailable = seatStatusRepository.findByName(status);
      seat.setSeatStatus(seatStatusAvailable);
      seatRepository.save(seat);
    } catch (Exception e) {
      throw new UpdateException("Error while rolling over seats availability: " + e.getMessage());
    }
  }

  /**
   * Seat Status are :
   *
   * @param ticketReservation
   * @return true if seat is available, false if the status is different from "available"
   */
  public Boolean checkIfSeatStatusIsAvailable(TicketReservation ticketReservation) {
    String seatStatus = ticketReservation.getId().getSeatId().getSeatStatus().getName();
    return seatStatus.equals("available");
  }
}
