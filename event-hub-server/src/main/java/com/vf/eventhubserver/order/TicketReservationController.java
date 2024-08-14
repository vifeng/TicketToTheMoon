package com.vf.eventhubserver.order;

import com.vf.eventhubserver.LocationResponseBuilder;
import com.vf.eventhubserver.exception.CreateException;
import com.vf.eventhubserver.exception.NullException;
import java.net.URI;
import java.util.Set;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@CrossOrigin
@RestController
@RequestMapping("/api/ticketReservations")
@Validated
public class TicketReservationController implements LocationResponseBuilder {

  private TicketReservationService ticketReservationService;

  public TicketReservationController(TicketReservationService ticketReservationService) {
    this.ticketReservationService = ticketReservationService;
  }

  @GetMapping
  public ResponseEntity<Set<TicketReservationDTO>> getAllTicketReservations() {
    return ResponseEntity.ok(ticketReservationService.findAll());
  }

  @GetMapping("/sessionevent/{sessioneventId}/seat/{seatId}")
  public ResponseEntity<TicketReservationDTO> getTicketReservationById(
      @PathVariable Long seatId, @PathVariable Long sessioneventId) {
    return ResponseEntity.ok(ticketReservationService.findById(sessioneventId, seatId));
  }

  @PostMapping
  public ResponseEntity<Void> createTicketReservation(
      @RequestBody TicketReservationDTO ticketReservationDTO)
      throws NullException, CreateException {
    return entityWithLocation(
        ticketReservationService.createTicketReservation(ticketReservationDTO));
  }

  private ResponseEntity<Void> entityWithLocation(TicketReservationKey resourceId) {
    URI location =
        ServletUriComponentsBuilder.fromCurrentRequestUri()
            .path("/sessionevent/{sessioneventId}/seat/{seatId}")
            .buildAndExpand(resourceId.getSessionEventId().getId(), resourceId.getSeatId().getId())
            .toUri();
    return ResponseEntity.created(location).build();
  }
}
