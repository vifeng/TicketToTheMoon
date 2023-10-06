package com.vf.tickettothemoon_BackEnd.domain.service.mappers;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.vf.tickettothemoon_BackEnd.domain.dto.TarificationDTO;
import com.vf.tickettothemoon_BackEnd.domain.model.Tarification;

@Mapper
public interface TarificationMapper {

    TarificationMapper INSTANCE = Mappers.getMapper(TarificationMapper.class);

    TarificationDTO toTarificationDTO(Tarification tarification);

    Tarification toTarification(TarificationDTO tarificationDTO);

    @IterableMapping(elementTargetType = TarificationDTO.class)
    Iterable<TarificationDTO> toTarificationDTOs(Iterable<Tarification> tarifications);

    @IterableMapping(elementTargetType = Tarification.class)
    Iterable<Tarification> toTarifications(Iterable<TarificationDTO> tarificationDTOs);

}
