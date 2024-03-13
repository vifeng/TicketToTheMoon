package com.vf.eventhubserver.domain.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vf.eventhubserver.domain.model.CategorySpatial;

public interface CategorySpatialRepository extends JpaRepository<CategorySpatial, Long> {
    public CategorySpatial findByName(String name);
}
