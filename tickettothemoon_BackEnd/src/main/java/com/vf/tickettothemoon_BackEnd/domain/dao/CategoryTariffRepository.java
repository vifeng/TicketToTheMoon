package com.vf.tickettothemoon_BackEnd.domain.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import com.vf.tickettothemoon_BackEnd.domain.model.CategoryTariff;

public interface CategoryTariffRepository extends JpaRepository<CategoryTariff, Long> {
    public CategoryTariff findByName(String name);
}
