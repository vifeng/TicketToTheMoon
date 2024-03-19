package com.vf.eventhubserver.show;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SessionEventRepository extends JpaRepository<SessionEvent, Long> {}
