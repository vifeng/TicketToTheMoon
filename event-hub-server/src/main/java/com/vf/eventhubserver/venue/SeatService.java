package com.vf.eventhubserver.venue;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SeatService {
  private SeatRepository seatRepository;
  private SeatMapper seatMapper;

  public SeatService(SeatRepository seatRepository, SeatMapper seatMapper) {
    this.seatRepository = seatRepository;
    this.seatMapper = seatMapper;
  }

  public List<SeatDTO> getSeatsFromConfigurationHall(Long configurationhallsId) {
    Iterable<Seat> seats = seatRepository.findByConfigurationHallId(configurationhallsId);
    return seatMapper.toDTOs(seats);
  }
}
