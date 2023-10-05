package com.vf.tickettothemoon_BackEnd.domain.service.mappers;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import com.vf.tickettothemoon_BackEnd.domain.dto.PaymentStatus_categoryDTO;
import com.vf.tickettothemoon_BackEnd.domain.model.PaymentStatus_category;

@Mapper
public interface PaymentStatus_categoryMapper {

    PaymentStatus_categoryMapper INSTANCE = Mappers.getMapper(PaymentStatus_categoryMapper.class);

    PaymentStatus_categoryDTO toPaymentStatus_categoryDTO(
            PaymentStatus_category paymentStatus_category);

    PaymentStatus_category toPaymentStatus_category(
            PaymentStatus_categoryDTO paymentStatus_categoryDTO);

    @IterableMapping(elementTargetType = PaymentStatus_categoryDTO.class)
    Iterable<PaymentStatus_categoryDTO> toPaymentStatus_categoriesDTOs(
            Iterable<PaymentStatus_category> paymentStatus_categories);


    @IterableMapping(elementTargetType = PaymentStatus_category.class)
    Iterable<PaymentStatus_category> toPaymentStatus_categories(
            Iterable<PaymentStatus_categoryDTO> paymentStatus_categoryDTOs);
}
