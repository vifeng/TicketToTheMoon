package com.vf.tickettothemoon_BackEnd.api;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.vf.tickettothemoon_BackEnd.domain.dto.Ticket_ReservationDTO;
import com.vf.tickettothemoon_BackEnd.domain.service.Ticket_ReservationService;
import com.vf.tickettothemoon_BackEnd.exception.CreateException;
import com.vf.tickettothemoon_BackEnd.exception.NullException;

@CrossOrigin
@RestController
@RequestMapping("/api/ticket_reservation")
public class Ticket_ReservationController {

    private Ticket_ReservationService ticket_ReservationService;

    @Autowired
    public Ticket_ReservationController(Ticket_ReservationService ticket_ReservationService) {
        this.ticket_ReservationService = ticket_ReservationService;
    }

    @GetMapping
    public ResponseEntity<List<Ticket_ReservationDTO>> getAllTicket_Reservations() {
        return ResponseEntity.ok(ticket_ReservationService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ticket_ReservationDTO> getTicket_ReservationById(Long id) {
        return ResponseEntity.ok(ticket_ReservationService.findById(id));
    }

    @PostMapping
    public ResponseEntity<Ticket_ReservationDTO> createTicket_Reservation(
            @RequestBody Ticket_ReservationDTO ticket_ReservationDTO)
            throws NullException, CreateException {
        if (ticket_ReservationDTO == null)
            throw new NullException("Ticket_Reservation post is null");
        return ResponseEntity
                .ok(ticket_ReservationService.createTicket_Reservation(ticket_ReservationDTO));
    }

    // TOFINISH: PUT, PATCH, DELETE

    ///////////////////////
    // RELATIONSHIP
    ///////////////////////


}
