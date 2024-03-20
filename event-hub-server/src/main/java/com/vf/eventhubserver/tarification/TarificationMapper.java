package com.vf.eventhubserver.tarification;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TarificationMapper {

  TarificationDTO toDTO(Tarification tarification);

  Tarification toEntity(TarificationDTO tarificationDTO);

  @IterableMapping(elementTargetType = TarificationDTO.class)
  Iterable<TarificationDTO> toDTOs(Iterable<Tarification> tarifications);

  @IterableMapping(elementTargetType = Tarification.class)
  Iterable<Tarification> toEntities(Iterable<TarificationDTO> tarificationDTOs);
}
