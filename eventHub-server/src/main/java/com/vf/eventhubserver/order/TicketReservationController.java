package com.vf.eventhubserver.order;

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
import com.vf.eventhubserver.exception.CreateException;
import com.vf.eventhubserver.exception.NullException;

@CrossOrigin
@RestController
@RequestMapping("/api/ticketReservation")
@Validated
public class TicketReservationController {

    private TicketReservationService ticketReservationService;

    public TicketReservationController(TicketReservationService ticketReservationService) {
        this.ticketReservationService = ticketReservationService;
    }

    @GetMapping
    public ResponseEntity<Set<TicketReservationDTO>> getAllTicketReservations() {
        return ResponseEntity.ok(ticketReservationService.findAll());
    }

    @GetMapping("/sessionevent/{sessioneventId}/seat/{seatId}")
    public ResponseEntity<TicketReservationDTO> getTicketReservationById(@PathVariable Long seatId,
            @PathVariable Long sessioneventId) {
        return ResponseEntity.ok(ticketReservationService.findById(sessioneventId, seatId));
    }

    @PostMapping
    public ResponseEntity<TicketReservationDTO> createTicketReservation(
            @RequestBody TicketReservationDTO ticketReservationDTO)
            throws NullException, CreateException {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ticketReservationService.createTicketReservation(ticketReservationDTO));
    }

}
