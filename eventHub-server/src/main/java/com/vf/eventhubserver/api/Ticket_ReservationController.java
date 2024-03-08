package com.vf.eventhubserver.api;

import java.util.Set;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.vf.eventhubserver.domain.dto.Ticket_ReservationDTO;
import com.vf.eventhubserver.domain.service.Ticket_ReservationService;
import com.vf.eventhubserver.exception.CreateException;
import com.vf.eventhubserver.exception.NullException;

@CrossOrigin
@RestController
@RequestMapping("/api/ticket_reservation")
@Validated
public class Ticket_ReservationController {

    private Ticket_ReservationService ticket_ReservationService;

    public Ticket_ReservationController(Ticket_ReservationService ticket_ReservationService) {
        this.ticket_ReservationService = ticket_ReservationService;
    }

    @GetMapping
    public ResponseEntity<Set<Ticket_ReservationDTO>> getAllTicket_Reservations() {
        return ResponseEntity.ok(ticket_ReservationService.findAll());
    }

    @GetMapping("/sessionevent/{sessioneventId}/seat/{seatId}")
    public ResponseEntity<Ticket_ReservationDTO> getTicket_ReservationById(
            @PathVariable Long seatId, @PathVariable Long sessioneventId) {
        return ResponseEntity.ok(ticket_ReservationService.findById(sessioneventId, seatId));
    }

    @PostMapping
    public ResponseEntity<Ticket_ReservationDTO> createTicket_Reservation(
            @RequestBody Ticket_ReservationDTO ticket_ReservationDTO)
            throws NullException, CreateException {
        if (ticket_ReservationDTO == null)
            throw new NullException("Ticket_Reservation post is null");
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ticket_ReservationService.createTicket_Reservation(ticket_ReservationDTO));
    }

    // TOFINISH: PUT, PATCH, DELETE

    ///////////////////////
    // RELATIONSHIP
    ///////////////////////


}
