package com.vf.tickettothemoon_BackEnd.domain.service.mappers;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import com.vf.tickettothemoon_BackEnd.domain.dto.PaymentDTO;
import com.vf.tickettothemoon_BackEnd.domain.model.Payment;

@Mapper
public interface PaymentMapper {

    PaymentMapper INSTANCE = Mappers.getMapper(PaymentMapper.class);

    PaymentDTO toPaymentDTO(Payment payment);

    Payment toPayment(PaymentDTO paymentDTO);


    @IterableMapping(elementTargetType = PaymentDTO.class)
    Iterable<PaymentDTO> toPaymentsDTOs(Iterable<Payment> payments);

    @IterableMapping(elementTargetType = Payment.class)
    Iterable<Payment> toPayments(Iterable<PaymentDTO> paymentDTOs);
}
