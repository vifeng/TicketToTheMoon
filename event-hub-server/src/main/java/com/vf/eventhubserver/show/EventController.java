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
  private final SessionEventService sessionEventService;

  public EventController(EventService eventService, SessionEventService sessionEventService) {
    this.eventService = eventService;
    this.sessionEventService = sessionEventService;
  }

  @GetMapping()
  public ResponseEntity<List<EventDTO>> getAllEvents() {
    return ResponseEntity.ok(eventService.findAll());
  }

  @GetMapping("/{eventId}")
  public ResponseEntity<EventDTO> getEventById(@PathVariable Long eventId) {
    return ResponseEntity.ok(eventService.getEventsById(eventId));
  }

  @GetMapping("/{eventId}/sessionsEvents")
  public ResponseEntity<List<SessionEventDTO>> getSessionEventsByEventId(
      @PathVariable Long eventId) {
    return ResponseEntity.ok(sessionEventService.getSessionEventsByEventId(eventId));
  }
}
