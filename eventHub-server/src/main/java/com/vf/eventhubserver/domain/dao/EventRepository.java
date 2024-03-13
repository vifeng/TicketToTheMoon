package com.vf.eventhubserver.domain.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vf.eventhubserver.domain.model.Event;

public interface EventRepository extends JpaRepository<Event, Long> {}
