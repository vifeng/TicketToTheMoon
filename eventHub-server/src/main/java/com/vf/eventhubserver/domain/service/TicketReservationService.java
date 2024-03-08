package com.vf.eventhubserver.domain.service;

import java.util.Collection;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.vf.eventhubserver.domain.dao.SeatRepository;
import com.vf.eventhubserver.domain.dao.SeatStatusRepository;
import com.vf.eventhubserver.domain.dao.SessionEventRepository;
import com.vf.eventhubserver.domain.dao.TicketReservationRepository;
import com.vf.eventhubserver.domain.dto.TicketReservationDTO;
import com.vf.eventhubserver.domain.model.Seat;
import com.vf.eventhubserver.domain.model.SeatStatus;
import com.vf.eventhubserver.domain.model.SessionEvent;
import com.vf.eventhubserver.domain.model.TicketReservation;
import com.vf.eventhubserver.domain.model.TicketReservationKey;
import com.vf.eventhubserver.domain.service.mapper.TicketReservationMapper;
import com.vf.eventhubserver.exception.CreateException;
import com.vf.eventhubserver.exception.DuplicateKeyException;
import com.vf.eventhubserver.exception.FinderException;
import com.vf.eventhubserver.exception.UpdateException;

@Service
@Transactional
public class TicketReservationService {
    private TicketReservationRepository ticket_ReservationRepository;
    private final TicketReservationMapper ticket_ReservationMapper;
    private SeatRepository seatRepository;
    private SessionEventRepository sessionEventRepository;
    private SeatStatusRepository seat_StatusRepository;
    private static final Logger log = LoggerFactory.getLogger(HallService.class);


    public TicketReservationService(TicketReservationRepository ticket_ReservationRepository,
            TicketReservationMapper ticket_ReservationMapper, SeatRepository seatRepository,
            SessionEventRepository sessionEventRepository,
            SeatStatusRepository seat_StatusRepository) {
        this.ticket_ReservationRepository = ticket_ReservationRepository;
        this.ticket_ReservationMapper = ticket_ReservationMapper;
        this.seatRepository = seatRepository;
        this.sessionEventRepository = sessionEventRepository;
        this.seat_StatusRepository = seat_StatusRepository;
    }


    public Set<TicketReservationDTO> findAll() throws FinderException {
        Iterable<TicketReservation> ticket_Reservations = ticket_ReservationRepository.findAll();
        int size = ((Collection<TicketReservation>) ticket_Reservations).size();
        if (size == 0)
            throw new FinderException("No ticket_Reservations in the database");
        Set<TicketReservationDTO> ticket_ReservationDTOs =
                ticket_ReservationMapper.toDTOs(ticket_Reservations);
        return ticket_ReservationDTOs;
    }



    public TicketReservationDTO findById(Long sessioneventId, Long seatId) throws FinderException {
        Seat seat = seatRepository.findById(seatId)
                .orElseThrow(() -> new FinderException("Seat with id " + seatId + " not found"));
        SessionEvent sessionEvent = sessionEventRepository.findById(sessioneventId).orElseThrow(
                () -> new FinderException("SessionEvent with id " + sessioneventId + " not found"));
        TicketReservationKey id = new TicketReservationKey(seat, sessionEvent);
        TicketReservation ticket_Reservation =
                ticket_ReservationRepository.findById(id).orElseThrow(() -> new FinderException(
                        "Ticket_Reservation with id " + id + " not found"));
        return ticket_ReservationMapper.toDTO(ticket_Reservation);
    }


    public TicketReservationDTO createTicket_Reservation(TicketReservationDTO ticket_ReservationDTO)
            throws IllegalArgumentException, CreateException, DuplicateKeyException {
        try {
            TicketReservation ticket_Reservation =
                    ticket_ReservationMapper.toEntity(ticket_ReservationDTO);
            ticket_ReservationRepository.findById(ticket_Reservation.getId())
                    .ifPresent(ticket_ReservationKey -> {
                        throw new DuplicateKeyException(
                                "Ticket_Reservation not created, The following id already exists in the database: "
                                        + ticket_ReservationDTO.ticket_ReservationKey());
                    });
            ticket_ReservationRepository.save(ticket_Reservation);
            TicketReservationDTO savedTicket_ReservationDTO =
                    ticket_ReservationMapper.toDTO(ticket_Reservation);
            return savedTicket_ReservationDTO;
        } catch (DuplicateKeyException e) {
            throw new DuplicateKeyException(e.getMessage());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Ticket_Reservation not created : " + e.getMessage(),
                    e);
        } catch (Exception e) {
            throw new CreateException("Ticket_Reservation not created : " + e.getMessage(), e);
        }
    }

    public void deleteById(TicketReservationKey id) {
        if (!ticket_ReservationRepository.existsById(id))
            throw new IllegalArgumentException("Ticket_Reservation with id " + id + " not found");
        ticket_ReservationRepository.deleteById(id);
    }

    // TOFINISH: PUT, PATCH, DELETE


    // UTILITIES
    public void changeSeatsStatus(TicketReservation ticket_Reservation, String status)
            throws UpdateException {
        try {
            // TODO_LOW : use SeatStatusService when it will be created
            // change the seat availbitity to true
            Seat seat = ticket_Reservation.getId().getSeatId();
            SeatStatus seat_Status_available = seat_StatusRepository.findByName(status);
            seat.setSeat_Status(seat_Status_available);
            seatRepository.save(seat);
        } catch (Exception e) {
            throw new UpdateException(
                    "Error while rolling over seats availability: " + e.getMessage());
        }
    }


    /**
     * Seat Status are :
     * 
     * @param ticket_Reservation
     * @return true if seat is available, false if the status is different from "available"
     */
    public Boolean checkIfSeatStatusIsAvailable(TicketReservation ticket_Reservation) {
        String seatStatus = ticket_Reservation.getId().getSeatId().getSeat_Status().getName();
        if (seatStatus.equals("available")) {
            return true;
        }
        return false;
    }



    ///////////////////////
    // RELATIONSHIP
    ///////////////////////


}
