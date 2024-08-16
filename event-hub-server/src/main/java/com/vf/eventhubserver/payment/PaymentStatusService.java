package com.vf.eventhubserver.payment;

import com.vf.eventhubserver.exception.FinderException;
import com.vf.eventhubserver.exception.NullException;
import java.util.Collection;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PaymentStatusService {
  PaymentStatusRepository paymentStatusRepository;
  PaymentStatusMapper paymentStatusMapper;

  public PaymentStatusService(
      PaymentStatusRepository paymentStatusRepository, PaymentStatusMapper paymentStatusMapper) {
    this.paymentStatusRepository = paymentStatusRepository;
    this.paymentStatusMapper = paymentStatusMapper;
  }

  public List<PaymentStatusDTO> findAll() throws FinderException {
    Iterable<PaymentStatus> paymentStatus = paymentStatusRepository.findAll();
    int size = ((Collection<PaymentStatus>) paymentStatus).size();
    if (size == 0) {
      throw new FinderException("No PaymentStatus_categorys in the database");
    }
    return paymentStatusMapper.toDTOs(paymentStatus);
  }

  public PaymentStatusDTO findById(Long id) throws FinderException {
    PaymentStatus paymentStatus =
        paymentStatusRepository
            .findById(id)
            .orElseThrow(() -> new FinderException("PaymentStatus with id {" + id + "} not found"));
    if (paymentStatus == null) {
      throw new NullException("PaymentStatus is null");
    }
    return paymentStatusMapper.toDTO(paymentStatus);
  }

  public PaymentStatusDTO findByPaymentStatusName(String status) throws FinderException {
    PaymentStatus paymentStatus =
        paymentStatusRepository
            .findByPaymentStatusName(status)
            .orElseThrow(
                () ->
                    new FinderException(
                        "PaymentStatus with Payment Status {" + status + "} not found"));
    if (paymentStatus == null) {
      throw new NullException("PaymentStatus is null");
    }
    return paymentStatusMapper.toDTO(paymentStatus);
  }
}
