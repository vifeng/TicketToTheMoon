package com.vf.eventhubserver.payment;

import java.util.List;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PaymentMapper {

    PaymentDTO toDTO(Payment payment);

    Payment toEntity(PaymentDTO paymentDTO);

    @IterableMapping(elementTargetType = PaymentDTO.class)
    List<PaymentDTO> toDTOs(Iterable<Payment> payments);

    @IterableMapping(elementTargetType = Payment.class)
    List<Payment> toEntities(Iterable<PaymentDTO> paymentDTOs);
}
