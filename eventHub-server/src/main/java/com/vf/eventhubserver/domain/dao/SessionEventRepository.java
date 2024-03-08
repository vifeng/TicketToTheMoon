package com.vf.eventhubserver.domain.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import com.vf.eventhubserver.domain.model.SessionEvent;

public interface SessionEventRepository extends JpaRepository<SessionEvent, Long> {
}
