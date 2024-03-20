package com.vf.eventhubserver.tarification;

import java.util.List;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryTariffMapper {

  CategoryTariffDTO toDTO(CategoryTariff categoryTariff);

  CategoryTariff toEntity(CategoryTariffDTO categoryTariffDTO);

  @IterableMapping(elementTargetType = CategoryTariffDTO.class)
  List<CategoryTariffDTO> toDTOs(Iterable<CategoryTariff> categoriesTariffs);

  @IterableMapping(elementTargetType = CategoryTariff.class)
  List<CategoryTariff> toEntities(Iterable<CategoryTariffDTO> categoriesTariffsDTOs);
}
