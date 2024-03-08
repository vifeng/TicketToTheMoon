package com.vf.eventhubserver.domain.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import com.vf.eventhubserver.domain.model.CategoryTariff;

public interface CategoryTariffRepository extends JpaRepository<CategoryTariff, Long> {
    public CategoryTariff findByName(String name);
}
