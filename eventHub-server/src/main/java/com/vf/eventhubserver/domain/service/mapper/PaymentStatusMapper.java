package com.vf.eventhubserver.domain.service.mapper;

import java.util.List;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import com.vf.eventhubserver.domain.dto.PaymentStatusDTO;
import com.vf.eventhubserver.domain.model.PaymentStatus;

@Mapper(componentModel = "spring")
public interface PaymentStatusMapper {


        PaymentStatusDTO toDTO(PaymentStatus paymentStatus_category);

        PaymentStatus toEntity(PaymentStatusDTO paymentStatus_categoryDTO);

        @IterableMapping(elementTargetType = PaymentStatusDTO.class)
        List<PaymentStatusDTO> toDTOs(Iterable<PaymentStatus> paymentStatus_categories);


        @IterableMapping(elementTargetType = PaymentStatus.class)
        List<PaymentStatus> toEntities(Iterable<PaymentStatusDTO> paymentStatus_categoryDTOs);
}
