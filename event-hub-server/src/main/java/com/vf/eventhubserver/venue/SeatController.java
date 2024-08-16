package com.vf.eventhubserver.venue;

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
@RequestMapping("/api/")
@Validated
public class SeatController implements LocationResponseBuilder {
  private final SeatService seatService;

  public SeatController(SeatService seatService) {
    this.seatService = seatService;
  }

  @GetMapping("configurationhalls/{configurationhallId}/seats")
  public ResponseEntity<List<SeatDTO>> getSeatsFromConfigurationHall(
      @PathVariable Long configurationhallId) {
    return ResponseEntity.ok(seatService.getSeatsFromConfigurationHall(configurationhallId));
  }
}
