package com.vf.tickettothemoon_BackEnd.domain.service.mappers;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import com.vf.tickettothemoon_BackEnd.domain.dto.PriceDTO;
import com.vf.tickettothemoon_BackEnd.domain.model.Price;

@Mapper
public interface PriceMapper {
    PriceMapper INSTANCE = Mappers.getMapper(PriceMapper.class);

    PriceDTO toPriceDTO(Price price);

    Price toPrice(PriceDTO priceDTO);

    @IterableMapping(elementTargetType = PriceDTO.class)
    Iterable<PriceDTO> toPriceDTOs(Iterable<Price> prices);

    @IterableMapping(elementTargetType = Price.class)
    Iterable<Price> toPrices(Iterable<PriceDTO> priceDTOs);


}
