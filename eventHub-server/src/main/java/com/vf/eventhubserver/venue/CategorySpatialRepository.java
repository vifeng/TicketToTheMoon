package com.vf.eventhubserver.venue;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CategorySpatialRepository extends JpaRepository<CategorySpatial, Long> {
    public CategorySpatial findByName(String name);
}
