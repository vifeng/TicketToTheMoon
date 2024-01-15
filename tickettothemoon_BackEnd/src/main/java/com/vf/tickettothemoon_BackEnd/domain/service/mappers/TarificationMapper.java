package com.vf.tickettothemoon_BackEnd.domain.service.mappers;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import com.vf.tickettothemoon_BackEnd.domain.dto.TarificationDTO;
import com.vf.tickettothemoon_BackEnd.domain.model.Tarification;

@Mapper(componentModel = "spring")
public interface TarificationMapper {

    TarificationDTO toDTO(Tarification tarification);

    Tarification toEntity(TarificationDTO tarificationDTO);

    @IterableMapping(elementTargetType = TarificationDTO.class)
    Iterable<TarificationDTO> toDTOs(Iterable<Tarification> tarifications);

    @IterableMapping(elementTargetType = Tarification.class)
    Iterable<Tarification> toEntities(Iterable<TarificationDTO> tarificationDTOs);

}
