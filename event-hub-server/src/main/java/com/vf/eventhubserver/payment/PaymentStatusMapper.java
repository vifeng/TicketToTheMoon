package com.vf.eventhubserver.payment;

import java.util.List;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PaymentStatusMapper {

  PaymentStatusDTO toDTO(PaymentStatus paymentStatus);

  PaymentStatus toEntity(PaymentStatusDTO paymentStatusDTO);

  @IterableMapping(elementTargetType = PaymentStatusDTO.class)
  List<PaymentStatusDTO> toDTOs(Iterable<PaymentStatus> paymentStatuses);

  @IterableMapping(elementTargetType = PaymentStatus.class)
  List<PaymentStatus> toEntities(Iterable<PaymentStatusDTO> paymentStatusDTOs);
}
