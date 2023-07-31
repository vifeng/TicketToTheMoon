package com.vf.tickettothemoon_BackEnd.domain.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import com.vf.tickettothemoon_BackEnd.domain.model.SessionEvent;

public interface SessionEventRepository extends JpaRepository<SessionEvent, Long> {
}
