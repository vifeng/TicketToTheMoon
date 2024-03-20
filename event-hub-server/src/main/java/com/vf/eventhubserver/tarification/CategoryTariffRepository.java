package com.vf.eventhubserver.tarification;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryTariffRepository extends JpaRepository<CategoryTariff, Long> {
  public CategoryTariff findByName(String name);
}
