package com.vf.tickettothemoon_BackEnd.domain.service.mappers;

import java.util.List;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import com.vf.tickettothemoon_BackEnd.domain.dto.Ticket_ReservationKeyDTO;
import com.vf.tickettothemoon_BackEnd.domain.model.Ticket_ReservationKey;

@Mapper
public interface Ticket_ReservationKeyMapper {

    Ticket_ReservationKeyMapper INSTANCE = Mappers.getMapper(Ticket_ReservationKeyMapper.class);

    Ticket_ReservationKeyDTO toTicket_ReservationKeyDTO(
            Ticket_ReservationKey ticket_ReservationKey);

    Ticket_ReservationKey toTicket_ReservationKey(
            Ticket_ReservationKeyDTO ticket_ReservationKeyDTO);

    @IterableMapping(elementTargetType = Ticket_ReservationKeyDTO.class)
    List<Ticket_ReservationKeyDTO> toTicket_ReservationKeyDTOs(
            Iterable<Ticket_ReservationKey> ticket_ReservationKeys);

    @IterableMapping(elementTargetType = Ticket_ReservationKey.class)
    List<Ticket_ReservationKey> toTicket_ReservationKeys(
            Iterable<Ticket_ReservationKeyDTO> ticket_ReservationKeyDTOs);

}
