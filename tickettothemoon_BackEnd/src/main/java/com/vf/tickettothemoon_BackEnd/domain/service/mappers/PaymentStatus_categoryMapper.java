package com.vf.tickettothemoon_BackEnd.domain.service.mappers;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import com.vf.tickettothemoon_BackEnd.domain.dto.PaymentStatus_categoryDTO;
import com.vf.tickettothemoon_BackEnd.domain.model.PaymentStatus_category;

@Mapper(componentModel = "spring")
public interface PaymentStatus_categoryMapper {


        PaymentStatus_categoryDTO toDTO(PaymentStatus_category paymentStatus_category);

        PaymentStatus_category toEntity(PaymentStatus_categoryDTO paymentStatus_categoryDTO);

        @IterableMapping(elementTargetType = PaymentStatus_categoryDTO.class)
        Iterable<PaymentStatus_categoryDTO> toDTOs(
                        Iterable<PaymentStatus_category> paymentStatus_categories);


        @IterableMapping(elementTargetType = PaymentStatus_category.class)
        Iterable<PaymentStatus_category> toEntities(
                        Iterable<PaymentStatus_categoryDTO> paymentStatus_categoryDTOs);
}
