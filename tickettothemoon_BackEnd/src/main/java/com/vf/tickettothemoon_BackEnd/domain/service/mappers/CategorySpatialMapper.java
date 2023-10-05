package com.vf.tickettothemoon_BackEnd.domain.service.mappers;

import java.util.List;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import com.vf.tickettothemoon_BackEnd.domain.dto.CategorySpatialDTO;
import com.vf.tickettothemoon_BackEnd.domain.model.CategorySpatial;

@Mapper
public interface CategorySpatialMapper {

    CategorySpatialMapper INSTANCE = Mappers.getMapper(CategorySpatialMapper.class);

    CategorySpatialDTO toCategorySpatialDTO(CategorySpatial category);

    CategorySpatial toCategorySpatial(CategorySpatialDTO categoryDTO);

    @IterableMapping(elementTargetType = CategorySpatialDTO.class)
    List<CategorySpatialDTO> toCategoriesSpatialDTOs(Iterable<CategorySpatial> categories);

    @IterableMapping(elementTargetType = CategorySpatial.class)
    List<CategorySpatial> toCategoriesSpatial(Iterable<CategorySpatialDTO> categoryDTOs);


}
