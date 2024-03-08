package com.vf.eventhubserver.domain.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import com.vf.eventhubserver.domain.model.ConfigurationHall;

public interface ConfigurationHallRepository extends JpaRepository<ConfigurationHall, Long> {
}
