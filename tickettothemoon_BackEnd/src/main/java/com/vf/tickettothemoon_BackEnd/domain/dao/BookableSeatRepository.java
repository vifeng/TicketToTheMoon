package com.vf.tickettothemoon_BackEnd.domain.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import com.vf.tickettothemoon_BackEnd.domain.model.BookableSeat;

public interface BookableSeatRepository extends JpaRepository<BookableSeat, Long> {
}
