package com.vf.tickettothemoon_BackEnd.domain.service.mappers;

import java.util.List;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import com.vf.tickettothemoon_BackEnd.domain.dto.CategoryTariffDTO;
import com.vf.tickettothemoon_BackEnd.domain.model.CategoryTariff;

@Mapper
public interface CategoryTariffMapper {

    CategoryTariffMapper INSTANCE = Mappers.getMapper(CategoryTariffMapper.class);

    CategoryTariffDTO toCategoryTariffDTO(CategoryTariff categoryTariff);

    CategoryTariff toCategoryTariff(CategoryTariffDTO categoryTariffDTO);

    @IterableMapping(elementTargetType = CategoryTariffDTO.class)
    List<CategoryTariffDTO> toCategoriesTariffDTOs(Iterable<CategoryTariff> categoriesTariffs);

    @IterableMapping(elementTargetType = CategoryTariff.class)
    List<CategoryTariff> toCategoriesTariffs(Iterable<CategoryTariffDTO> categoriesTariffsDTOs);

}
