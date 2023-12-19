package com.vf.tickettothemoon_BackEnd.domain.service;

import java.util.Collection;
import java.util.List;
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
import com.vf.tickettothemoon_BackEnd.exception.FinderException;

@Service
@Transactional
public class Ticket_ReservationService {
    private Ticket_ReservationRepository ticket_ReservationRepository;
    private final Ticket_ReservationMapper ticket_ReservationMapper;
    private SeatRepository seatRepository;
    private SessionEventRepository sessionEventRepository;


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
                ticket_ReservationMapper.toTicket_ReservationDTOs(ticket_Reservations);
        return ticket_ReservationDTOs;
    }



    public Ticket_ReservationDTO findById(Long seatId, Long eventId) throws FinderException {
        Seat seat = seatRepository.findById(seatId)
                .orElseThrow(() -> new FinderException("Seat with id " + seatId + " not found"));
        SessionEvent sessionEvent = sessionEventRepository.findById(eventId).orElseThrow(
                () -> new FinderException("SessionEvent with id " + eventId + " not found"));
        Ticket_ReservationKey id = new Ticket_ReservationKey(seat, sessionEvent);
        Ticket_Reservation ticket_Reservation =
                ticket_ReservationRepository.findById(id).orElseThrow(() -> new FinderException(
                        "Ticket_Reservation with id " + id + " not found"));
        return ticket_ReservationMapper.toTicket_ReservationDTO(ticket_Reservation);
    }


    public Ticket_ReservationDTO createTicket_Reservation(
            Ticket_ReservationDTO ticket_ReservationDTO)
            throws IllegalArgumentException, CreateException {
        // FIXME: I have a duplicate key exception here
        // if (ticket_ReservationDTO.ticket_ReservationKey() != null)
        // throw new DuplicateKeyException("Ticket_Reservation with id "
        // + ticket_ReservationDTO.ticket_ReservationKey() + " already exists");
        try {
            Ticket_Reservation ticket_Reservation =
                    ticket_ReservationMapper.toTicket_Reservation(ticket_ReservationDTO);
            ticket_ReservationRepository.save(ticket_Reservation);
            Ticket_ReservationDTO savedTicket_ReservationDTO =
                    ticket_ReservationMapper.toTicket_ReservationDTO(ticket_Reservation);
            return savedTicket_ReservationDTO;
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Ticket_Reservation not created : " + e.getMessage(),
                    e);
        } catch (Exception e) {
            throw new CreateException("Ticket_Reservation not created : " + e.getMessage(), e);
        }
    }

    // TOFINISH:

    ///////////////////////
    // RELATIONSHIP
    ///////////////////////


}
