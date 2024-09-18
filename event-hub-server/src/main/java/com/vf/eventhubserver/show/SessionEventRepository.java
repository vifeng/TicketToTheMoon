package com.vf.eventhubserver.show;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SessionEventRepository extends JpaRepository<SessionEvent, Long> {

  // Display all sessionEvents by event id
  List<SessionEvent> findAllByEventId(Long eventId);
}
