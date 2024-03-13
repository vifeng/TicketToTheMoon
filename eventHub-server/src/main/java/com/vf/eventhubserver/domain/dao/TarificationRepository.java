package com.vf.eventhubserver.domain.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vf.eventhubserver.domain.model.Tarification;

public interface TarificationRepository extends JpaRepository<Tarification, Long> {}
