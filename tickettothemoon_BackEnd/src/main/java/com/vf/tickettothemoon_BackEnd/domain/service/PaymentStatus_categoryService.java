package com.vf.tickettothemoon_BackEnd.domain.service;

import java.util.Collection;
import java.util.List;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import com.vf.tickettothemoon_BackEnd.domain.dao.PaymentStatus_categoryRepository;
import com.vf.tickettothemoon_BackEnd.domain.dto.PaymentStatus_categoryDTO;
import com.vf.tickettothemoon_BackEnd.domain.model.PaymentStatus_category;
import com.vf.tickettothemoon_BackEnd.domain.service.mappers.PaymentStatus_categoryMapper;
import com.vf.tickettothemoon_BackEnd.exception.FinderException;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class PaymentStatus_categoryService {
    PaymentStatus_categoryRepository paymentStatus_categoryRepository;
    PaymentStatus_categoryMapper paymentStatus_categoryMapper;

    public PaymentStatus_categoryService(
            PaymentStatus_categoryRepository paymentStatus_categoryRepository,
            PaymentStatus_categoryMapper paymentStatus_categoryMapper) {
        this.paymentStatus_categoryRepository = paymentStatus_categoryRepository;
        this.paymentStatus_categoryMapper = paymentStatus_categoryMapper;
    }

    public List<PaymentStatus_categoryDTO> findAll() throws FinderException {
        Iterable<PaymentStatus_category> paymentStatus_categorys =
                paymentStatus_categoryRepository.findAll();
        int size = ((Collection<PaymentStatus_category>) paymentStatus_categorys).size();
        if (size == 0) {
            throw new FinderException("No PaymentStatus_categorys in the database");
        }
        List<PaymentStatus_categoryDTO> paymentStatus_categoryDTOs =
                paymentStatus_categoryMapper.toDTOs(paymentStatus_categorys);
        return paymentStatus_categoryDTOs;
    }

    public PaymentStatus_categoryDTO findById(@NonNull Long id) throws FinderException {
        PaymentStatus_category paymentStatus_category =
                paymentStatus_categoryRepository.findById(id).orElseThrow(() -> new FinderException(
                        "PaymentStatus_category with id {" + id + "} not found"));
        return paymentStatus_categoryMapper.toDTO(paymentStatus_category);
    }

    public PaymentStatus_categoryDTO findByPaymentStatusName(String status) throws FinderException {
        PaymentStatus_category paymentStatus_category = paymentStatus_categoryRepository
                .findByPaymentStatusName(status).orElseThrow(() -> new FinderException(
                        "PaymentStatus_category with Payment Status {" + status + "} not found"));
        return paymentStatus_categoryMapper.toDTO(paymentStatus_category);
    }
}
