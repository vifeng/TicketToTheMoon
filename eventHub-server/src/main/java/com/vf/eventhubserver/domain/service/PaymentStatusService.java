package com.vf.eventhubserver.domain.service;

import java.util.Collection;
import java.util.List;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import com.vf.eventhubserver.domain.dao.PaymentStatusRepository;
import com.vf.eventhubserver.domain.dto.PaymentStatusDTO;
import com.vf.eventhubserver.domain.model.PaymentStatus;
import com.vf.eventhubserver.domain.service.mapper.PaymentStatusMapper;
import com.vf.eventhubserver.exception.FinderException;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class PaymentStatusService {
    PaymentStatusRepository paymentStatus_categoryRepository;
    PaymentStatusMapper paymentStatus_categoryMapper;

    public PaymentStatusService(PaymentStatusRepository paymentStatus_categoryRepository,
            PaymentStatusMapper paymentStatus_categoryMapper) {
        this.paymentStatus_categoryRepository = paymentStatus_categoryRepository;
        this.paymentStatus_categoryMapper = paymentStatus_categoryMapper;
    }

    public List<PaymentStatusDTO> findAll() throws FinderException {
        Iterable<PaymentStatus> paymentStatus_categorys =
                paymentStatus_categoryRepository.findAll();
        int size = ((Collection<PaymentStatus>) paymentStatus_categorys).size();
        if (size == 0) {
            throw new FinderException("No PaymentStatus_categorys in the database");
        }
        List<PaymentStatusDTO> paymentStatus_categoryDTOs =
                paymentStatus_categoryMapper.toDTOs(paymentStatus_categorys);
        return paymentStatus_categoryDTOs;
    }

    public PaymentStatusDTO findById(@NonNull Long id) throws FinderException {
        PaymentStatus paymentStatus_category =
                paymentStatus_categoryRepository.findById(id).orElseThrow(() -> new FinderException(
                        "PaymentStatus_category with id {" + id + "} not found"));
        return paymentStatus_categoryMapper.toDTO(paymentStatus_category);
    }

    public PaymentStatusDTO findByPaymentStatusName(String status) throws FinderException {
        PaymentStatus paymentStatus_category = paymentStatus_categoryRepository
                .findByPaymentStatusName(status).orElseThrow(() -> new FinderException(
                        "PaymentStatus_category with Payment Status {" + status + "} not found"));
        return paymentStatus_categoryMapper.toDTO(paymentStatus_category);
    }
}
