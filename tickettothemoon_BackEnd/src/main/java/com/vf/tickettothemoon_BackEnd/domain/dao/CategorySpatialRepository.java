package com.vf.tickettothemoon_BackEnd.domain.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import com.vf.tickettothemoon_BackEnd.domain.model.CategorySpatial;

public interface CategorySpatialRepository extends JpaRepository<CategorySpatial, Long> {
    public CategorySpatial findByName(String name);
}
