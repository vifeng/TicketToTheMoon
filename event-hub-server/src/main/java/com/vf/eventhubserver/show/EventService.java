package com.vf.eventhubserver.show;

import com.vf.eventhubserver.exception.FinderException;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class EventService {

  private EventRepository eventRepository;
  private EventMapper eventMapper;

  public EventService(EventRepository eventRepository, EventMapper eventMapper) {
    this.eventRepository = eventRepository;
    this.eventMapper = eventMapper;
  }

  /**
   * @return all the events in the database.
   */
  public List<EventDTO> findAll() throws FinderException {
    List<Event> events = eventRepository.findAll();
    if (events.isEmpty()) {
      throw new FinderException("No events found");
    }
    return eventMapper.toDTOs(events);
  }

  /**
   * @param eventId
   * @return the event with the given id.
   */
  public EventDTO getEventsById(Long eventId) throws FinderException {
    Event event =
        eventRepository
            .findById(eventId)
            .orElseThrow(() -> new FinderException("Event with id " + eventId + " not found"));
    return eventMapper.toDTO(event);
  }
}
