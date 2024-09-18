package com.vf.eventhubserver.show;

import com.vf.eventhubserver.exception.FinderException;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SessionEventService {

  private SessionEventRepository sessionEventRepository;
  private SessionEventMapper sessionEventMapper;

  public SessionEventService(
      SessionEventRepository sessionEventRepository, SessionEventMapper sessionEventMapper) {
    this.sessionEventRepository = sessionEventRepository;
    this.sessionEventMapper = sessionEventMapper;
  }

  /**
   * @return all the session events in the database.
   */
  public List<SessionEventDTO> findAll() throws FinderException {
    List<SessionEvent> sessionEvents = sessionEventRepository.findAll();
    if (sessionEvents.isEmpty()) {
      throw new FinderException("No session events found");
    }
    return sessionEventMapper.toDTOs(sessionEvents);
  }

  public SessionEventDTO getSessionEventsById(Long sessionEventId) throws FinderException {
    SessionEvent sessionEvent =
        sessionEventRepository
            .findById(sessionEventId)
            .orElseThrow(
                () ->
                    new FinderException("Session event with id " + sessionEventId + " not found"));
    return sessionEventMapper.toDTO(sessionEvent);
  }

  public List<SessionEventDTO> getSessionEventsByEventId(Long eventId) throws FinderException {
    List<SessionEvent> sessionEvents = sessionEventRepository.findAllByEventId(eventId);
    if (sessionEvents.isEmpty()) {
      throw new FinderException("No session events found for event with id " + eventId);
    }
    return sessionEventMapper.toDTOs(sessionEvents);
  }
}
