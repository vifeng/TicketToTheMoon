package com.vf.tickettothemoon_BackEnd.domain.service.mappers;

import java.util.List;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import com.vf.tickettothemoon_BackEnd.domain.dto.BookableSeatDTO;
import com.vf.tickettothemoon_BackEnd.domain.model.BookableSeat;


@Mapper
public interface BookableSeatMapper {
    BookableSeatMapper INSTANCE = Mappers.getMapper(BookableSeatMapper.class);

    BookableSeatDTO toBookableSeatDTO(BookableSeat bookableSeat);

    BookableSeat toBookableSeat(BookableSeatDTO bookableSeatDTO);

    @IterableMapping(elementTargetType = BookableSeatDTO.class)
    List<BookableSeatDTO> toBookableSeatDTOs(Iterable<BookableSeat> bookableSeats);

    @IterableMapping(elementTargetType = BookableSeat.class)
    List<BookableSeat> toBookableSeats(Iterable<BookableSeatDTO> bookableSeatDTOs);

}
