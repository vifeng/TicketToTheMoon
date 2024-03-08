package com.vf.eventhubserver.domain.service.mapper;

import java.util.List;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import com.vf.eventhubserver.domain.dto.CategoryTariffDTO;
import com.vf.eventhubserver.domain.model.CategoryTariff;

@Mapper(componentModel = "spring")
public interface CategoryTariffMapper {


    CategoryTariffDTO toDTO(CategoryTariff categoryTariff);

    CategoryTariff toEntity(CategoryTariffDTO categoryTariffDTO);

    @IterableMapping(elementTargetType = CategoryTariffDTO.class)
    List<CategoryTariffDTO> toDTOs(Iterable<CategoryTariff> categoriesTariffs);

    @IterableMapping(elementTargetType = CategoryTariff.class)
    List<CategoryTariff> toEntities(Iterable<CategoryTariffDTO> categoriesTariffsDTOs);

}
