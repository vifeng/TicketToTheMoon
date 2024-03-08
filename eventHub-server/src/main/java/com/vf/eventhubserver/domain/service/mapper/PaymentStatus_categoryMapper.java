package com.vf.eventhubserver.domain.service.mapper;

import java.util.List;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import com.vf.eventhubserver.domain.dto.PaymentStatus_categoryDTO;
import com.vf.eventhubserver.domain.model.PaymentStatus_category;

@Mapper(componentModel = "spring")
public interface PaymentStatus_categoryMapper {


        PaymentStatus_categoryDTO toDTO(PaymentStatus_category paymentStatus_category);

        PaymentStatus_category toEntity(PaymentStatus_categoryDTO paymentStatus_categoryDTO);

        @IterableMapping(elementTargetType = PaymentStatus_categoryDTO.class)
        List<PaymentStatus_categoryDTO> toDTOs(
                        Iterable<PaymentStatus_category> paymentStatus_categories);


        @IterableMapping(elementTargetType = PaymentStatus_category.class)
        List<PaymentStatus_category> toEntities(
                        Iterable<PaymentStatus_categoryDTO> paymentStatus_categoryDTOs);
}
