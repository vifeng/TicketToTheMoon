package com.vf.tickettothemoon_BackEnd.domain.service;

import java.util.Collection;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.vf.tickettothemoon_BackEnd.domain.dao.SeatRepository;
import com.vf.tickettothemoon_BackEnd.domain.dao.Seat_StatusRepository;
import com.vf.tickettothemoon_BackEnd.domain.dao.SessionEventRepository;
import com.vf.tickettothemoon_BackEnd.domain.dao.Ticket_ReservationRepository;
import com.vf.tickettothemoon_BackEnd.domain.dto.Ticket_ReservationDTO;
import com.vf.tickettothemoon_BackEnd.domain.model.Seat;
import com.vf.tickettothemoon_BackEnd.domain.model.Seat_Status;
import com.vf.tickettothemoon_BackEnd.domain.model.SessionEvent;
import com.vf.tickettothemoon_BackEnd.domain.model.Ticket_Reservation;
import com.vf.tickettothemoon_BackEnd.domain.model.Ticket_ReservationKey;
import com.vf.tickettothemoon_BackEnd.domain.service.mappers.Ticket_ReservationMapper;
import com.vf.tickettothemoon_BackEnd.exception.CreateException;
import com.vf.tickettothemoon_BackEnd.exception.DuplicateKeyException;
import com.vf.tickettothemoon_BackEnd.exception.FinderException;
import com.vf.tickettothemoon_BackEnd.exception.UpdateException;

@Service
@Transactional
public class Ticket_ReservationService {
    private Ticket_ReservationRepository ticket_ReservationRepository;
    private final Ticket_ReservationMapper ticket_ReservationMapper;
    private SeatRepository seatRepository;
    private SessionEventRepository sessionEventRepository;
    private Seat_StatusRepository seat_StatusRepository;
    private static final Logger log = LoggerFactory.getLogger(HallService.class);


    public Ticket_ReservationService(Ticket_ReservationRepository ticket_ReservationRepository,
            Ticket_ReservationMapper ticket_ReservationMapper, SeatRepository seatRepository,
            SessionEventRepository sessionEventRepository,
            Seat_StatusRepository seat_StatusRepository) {
        this.ticket_ReservationRepository = ticket_ReservationRepository;
        this.ticket_ReservationMapper = ticket_ReservationMapper;
        this.seatRepository = seatRepository;
        this.sessionEventRepository = sessionEventRepository;
        this.seat_StatusRepository = seat_StatusRepository;
    }


    public Set<Ticket_ReservationDTO> findAll() throws FinderException {
        Iterable<Ticket_Reservation> ticket_Reservations = ticket_ReservationRepository.findAll();
        int size = ((Collection<Ticket_Reservation>) ticket_Reservations).size();
        if (size == 0)
            throw new FinderException("No ticket_Reservations in the database");
        Set<Ticket_ReservationDTO> ticket_ReservationDTOs =
                ticket_ReservationMapper.toDTOs(ticket_Reservations);
        return ticket_ReservationDTOs;
    }



    public Ticket_ReservationDTO findById(Long sessioneventId, Long seatId) throws FinderException {
        Seat seat = seatRepository.findById(seatId)
                .orElseThrow(() -> new FinderException("Seat with id " + seatId + " not found"));
        SessionEvent sessionEvent = sessionEventRepository.findById(sessioneventId).orElseThrow(
                () -> new FinderException("SessionEvent with id " + sessioneventId + " not found"));
        Ticket_ReservationKey id = new Ticket_ReservationKey(seat, sessionEvent);
        Ticket_Reservation ticket_Reservation =
                ticket_ReservationRepository.findById(id).orElseThrow(() -> new FinderException(
                        "Ticket_Reservation with id " + id + " not found"));
        return ticket_ReservationMapper.toDTO(ticket_Reservation);
    }


    public Ticket_ReservationDTO createTicket_Reservation(
            Ticket_ReservationDTO ticket_ReservationDTO)
            throws IllegalArgumentException, CreateException, DuplicateKeyException {
        try {
            Ticket_Reservation ticket_Reservation =
                    ticket_ReservationMapper.toEntity(ticket_ReservationDTO);
            ticket_ReservationRepository.findById(ticket_Reservation.getId())
                    .ifPresent(ticket_ReservationKey -> {
                        throw new DuplicateKeyException(
                                "Ticket_Reservation not created, The following id already exists in the database: "
                                        + ticket_ReservationDTO.ticket_ReservationKey());
                    });
            ticket_ReservationRepository.save(ticket_Reservation);
            Ticket_ReservationDTO savedTicket_ReservationDTO =
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

    public void deleteById(Ticket_ReservationKey id) {
        if (!ticket_ReservationRepository.existsById(id))
            throw new IllegalArgumentException("Ticket_Reservation with id " + id + " not found");
        ticket_ReservationRepository.deleteById(id);
    }

    // TOFINISH: PUT, PATCH, DELETE


    // UTILITIES
    public void changeSeatsStatus(Ticket_Reservation ticket_Reservation, String status)
            throws UpdateException {
        try {
            // TODO_LOW : use SeatStatusService when it will be created
            // change the seat availbitity to true
            Seat seat = ticket_Reservation.getId().getSeatId();
            Seat_Status seat_Status_available = seat_StatusRepository.findByName(status);
            seat.setSeat_Status(seat_Status_available);
            seatRepository.save(seat);
        } catch (Exception e) {
            throw new UpdateException(
                    "Error while rolling over seats availability: " + e.getMessage());
        }
    }


    public Boolean checkIfSeatStatusIsAvailable(Ticket_Reservation ticket_Reservation) {
        if (ticket_Reservation.getId().getSeatId().getSeat_Status().getName().equals("available")) {
            return true;
        }
        return false;
    }



    ///////////////////////
    // RELATIONSHIP
    ///////////////////////


}
