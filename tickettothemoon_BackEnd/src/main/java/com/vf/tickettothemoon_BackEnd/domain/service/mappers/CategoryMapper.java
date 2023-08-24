package com.vf.tickettothemoon_BackEnd.domain.service.mappers;

import java.util.List;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import com.vf.tickettothemoon_BackEnd.domain.dto.CategorySpatialDTO;
import com.vf.tickettothemoon_BackEnd.domain.model.CategorySpatial;

@Mapper
public interface CategoryMapper {

    CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

    CategorySpatialDTO toCategoryDTO(CategorySpatial category);

    CategorySpatial toCategory(CategorySpatialDTO categoryDTO);

    @IterableMapping(elementTargetType = CategorySpatialDTO.class)
    List<CategorySpatialDTO> toCategoryDTOs(Iterable<CategorySpatial> categories);

    @IterableMapping(elementTargetType = CategorySpatial.class)
    List<CategorySpatial> toCategories(Iterable<CategorySpatialDTO> categoryDTOs);


}
