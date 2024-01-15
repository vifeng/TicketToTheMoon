package com.vf.tickettothemoon_BackEnd.domain.service.mappers;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import com.vf.tickettothemoon_BackEnd.domain.dto.PaymentDTO;
import com.vf.tickettothemoon_BackEnd.domain.model.Payment;

@Mapper(componentModel = "spring")
public interface PaymentMapper {

    PaymentDTO toDTO(Payment payment);

    Payment toEntity(PaymentDTO paymentDTO);


    @IterableMapping(elementTargetType = PaymentDTO.class)
    Iterable<PaymentDTO> toDTOs(Iterable<Payment> payments);

    @IterableMapping(elementTargetType = Payment.class)
    Iterable<Payment> toEntities(Iterable<PaymentDTO> paymentDTOs);
}
