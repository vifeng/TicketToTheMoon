package com.vf.eventhubserver.venue;

import java.util.List;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategorySpatialMapper {

    CategorySpatialDTO toDTO(CategorySpatial category);

    CategorySpatial toEntity(CategorySpatialDTO categoryDTO);

    @IterableMapping(elementTargetType = CategorySpatialDTO.class)
    List<CategorySpatialDTO> toDTOs(Iterable<CategorySpatial> categories);

    @IterableMapping(elementTargetType = CategorySpatial.class)
    List<CategorySpatial> toEntities(Iterable<CategorySpatialDTO> categoryDTOs);

}
