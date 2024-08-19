package com.vf.eventhubserver.show;

import com.vf.eventhubserver.LocationResponseBuilder;
import java.util.List;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/api/sessionevents")
@Validated
public class SessionEventController implements LocationResponseBuilder {
  private final SessionEventService sessionEventService;

  public SessionEventController(SessionEventService sessionEventService) {
    this.sessionEventService = sessionEventService;
  }

  @GetMapping()
  public List<SessionEventDTO> getAllSessionEvents() {
    return sessionEventService.findAll();
  }

  @GetMapping("/{sessionEventId}")
  public SessionEventDTO getSessionEventById(@PathVariable Long sessionEventId) {
    return sessionEventService.getSessionEventsById(sessionEventId);
  }
}
