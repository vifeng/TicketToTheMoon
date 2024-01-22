package com.vf.tickettothemoon_BackEnd.domain.service.mappers;

import java.util.List;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import com.vf.tickettothemoon_BackEnd.domain.dto.PaymentDTO;
import com.vf.tickettothemoon_BackEnd.domain.model.Payment;

@Mapper(componentModel = "spring")
public interface PaymentMapper {

    PaymentDTO toDTO(Payment payment);

    Payment toEntity(PaymentDTO paymentDTO);


    @IterableMapping(elementTargetType = PaymentDTO.class)
    List<PaymentDTO> toDTOs(Iterable<Payment> payments);

    @IterableMapping(elementTargetType = Payment.class)
    List<Payment> toEntities(Iterable<PaymentDTO> paymentDTOs);
}
