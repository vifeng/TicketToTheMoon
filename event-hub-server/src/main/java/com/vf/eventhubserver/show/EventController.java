package com.vf.eventhubserver.show;

import com.vf.eventhubserver.LocationResponseBuilder;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/api/events")
@Validated
public class EventController implements LocationResponseBuilder {
  private final EventService eventService;

  public EventController(EventService eventService) {
    this.eventService = eventService;
  }

  @GetMapping()
  public ResponseEntity<List<EventDTO>> getAllEvents() {
    return ResponseEntity.ok(eventService.findAll());
  }

  @GetMapping("/{eventId}")
  public ResponseEntity<EventDTO> getEventById(@PathVariable Long eventId) {
    return ResponseEntity.ok(eventService.getEventsById(eventId));
  }
}
