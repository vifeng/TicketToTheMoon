package com.vf.tickettothemoon_BackEnd.domain.service;

import java.util.Collection;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vf.tickettothemoon_BackEnd.domain.dao.Ticket_ReservationRepository;
import com.vf.tickettothemoon_BackEnd.domain.dto.Ticket_ReservationDTO;
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
    private ObjectMapper objectMapper;

    @Autowired
    public Ticket_ReservationService(Ticket_ReservationRepository ticket_ReservationRepository,
            ObjectMapper objectMapper) {
        this.ticket_ReservationRepository = ticket_ReservationRepository;
        this.objectMapper = objectMapper;
    }


    public List<Ticket_ReservationDTO> findAll() throws FinderException {
        Iterable<Ticket_Reservation> ticket_Reservations = ticket_ReservationRepository.findAll();;
        int size = ((Collection<Ticket_Reservation>) ticket_Reservations).size();
        if (size == 0)
            throw new FinderException("No ticket_Reservations in the database");
        List<Ticket_ReservationDTO> ticket_ReservationDTOs =
                Ticket_ReservationMapper.INSTANCE.toTicket_ReservationDTOs(ticket_Reservations);
        return ticket_ReservationDTOs;
    }

    public Ticket_ReservationDTO findById(Ticket_ReservationKey id) throws FinderException {
        Ticket_Reservation ticket_Reservation =
                ticket_ReservationRepository.findById(id).orElseThrow(() -> new FinderException(
                        "Ticket_Reservation with id " + id + " not found"));
        return Ticket_ReservationMapper.INSTANCE.toTicket_ReservationDTO(ticket_Reservation);
    }


    public Ticket_ReservationDTO createTicket_Reservation(
            Ticket_ReservationDTO ticket_ReservationDTO)
            throws IllegalArgumentException, CreateException {
        if (ticket_ReservationDTO.getId() != null)
            throw new DuplicateKeyException("Ticket_Reservation with id "
                    + ticket_ReservationDTO.getId() + " already exists");
        try {
            Ticket_Reservation ticket_Reservation =
                    Ticket_ReservationMapper.INSTANCE.toTicket_Reservation(ticket_ReservationDTO);
            ticket_ReservationRepository.save(ticket_Reservation);
            Ticket_ReservationDTO savedTicket_ReservationDTO =
                    Ticket_ReservationMapper.INSTANCE.toTicket_ReservationDTO(ticket_Reservation);
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
