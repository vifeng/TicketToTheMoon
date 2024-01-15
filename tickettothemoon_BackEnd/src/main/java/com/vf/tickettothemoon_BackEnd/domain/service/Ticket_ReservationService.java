package com.vf.tickettothemoon_BackEnd.domain.service;

import java.util.Collection;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.vf.tickettothemoon_BackEnd.domain.dao.SeatRepository;
import com.vf.tickettothemoon_BackEnd.domain.dao.SessionEventRepository;
import com.vf.tickettothemoon_BackEnd.domain.dao.Ticket_ReservationRepository;
import com.vf.tickettothemoon_BackEnd.domain.dto.Ticket_ReservationDTO;
import com.vf.tickettothemoon_BackEnd.domain.model.Seat;
import com.vf.tickettothemoon_BackEnd.domain.model.SessionEvent;
import com.vf.tickettothemoon_BackEnd.domain.model.Ticket_Reservation;
import com.vf.tickettothemoon_BackEnd.domain.model.Ticket_ReservationKey;
import com.vf.tickettothemoon_BackEnd.domain.service.mappers.Ticket_ReservationMapper;
import com.vf.tickettothemoon_BackEnd.exception.CreateException;
import com.vf.tickettothemoon_BackEnd.exception.DuplicateKeyException;
import com.vf.tickettothemoon_BackEnd.exception.FinderException;

@Service
@Transactional
public class Ticket_ReservationService {
    private Ticket_ReservationRepository ticket_ReservationRepository;
    private final Ticket_ReservationMapper ticket_ReservationMapper;
    private SeatRepository seatRepository;
    private SessionEventRepository sessionEventRepository;

    private static final Logger log = LoggerFactory.getLogger(HallService.class);


    public Ticket_ReservationService(Ticket_ReservationRepository ticket_ReservationRepository,
            Ticket_ReservationMapper ticket_ReservationMapper, SeatRepository seatRepository,
            SessionEventRepository sessionEventRepository) {
        this.ticket_ReservationRepository = ticket_ReservationRepository;
        this.ticket_ReservationMapper = ticket_ReservationMapper;
        this.seatRepository = seatRepository;
        this.sessionEventRepository = sessionEventRepository;
    }


    public List<Ticket_ReservationDTO> findAll() throws FinderException {
        Iterable<Ticket_Reservation> ticket_Reservations = ticket_ReservationRepository.findAll();
        int size = ((Collection<Ticket_Reservation>) ticket_Reservations).size();
        if (size == 0)
            throw new FinderException("No ticket_Reservations in the database");
        List<Ticket_ReservationDTO> ticket_ReservationDTOs =
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


    // TODO : Booking_FK to complete
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

    // TOFINISH: PUT, PATCH, DELETE

    ///////////////////////
    // RELATIONSHIP
    ///////////////////////


}
