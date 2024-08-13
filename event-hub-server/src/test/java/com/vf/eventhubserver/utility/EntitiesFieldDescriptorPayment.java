package com.vf.eventhubserver.utility;

import com.vf.eventhubserver.payment.Payment;
import com.vf.eventhubserver.payment.PaymentStatus;
import java.util.Map;
import org.springframework.restdocs.constraints.ConstraintDescriptions;
import org.springframework.restdocs.payload.FieldDescriptor;

public class EntitiesFieldDescriptorPayment implements EntitiesFieldDescriptor {

  ConstraintDescriptions descPayment = new ConstraintDescriptions(Payment.class);
  ConstraintDescriptions descPaymentStatus = new ConstraintDescriptions(PaymentStatus.class);

  public FieldDescriptor[] generatePaymentFields(boolean includeId) {
    Map<String, String> customDescriptions =
        Map.of(
            "id", "",
            "paymentDateTime", "The date and time the payment was made. ",
            "booking", "The booking for which the payment was made. ",
            "paymentStatus", "The status of the payment. ");
    return generateFields(
        includeId,
        "payment",
        descPayment,
        customDescriptions,
        new String[] {"paymentDateTime", "booking", "paymentStatus"});
  }

  public FieldDescriptor[] generatePaymentStatusFields(boolean includeId) {
    Map<String, String> customDescriptions =
        Map.of(
            "id",
            "",
            "paymentStatusName",
            "The name of the payment status:"
                + "- PENDING: Payment is initiated but not yet completed"
                + "- COMPLETED: Payment was successfully completed. "
                + "- FAILED: Payment failed. "
                + "- CANCELLED: Payment was cancelled by user or system. "
                + "- REFUNDED: Payment was refunded. "
                + "- DECLINED: Payment was declined by the payment gateway. "
                + "- PROCESSING: Payment is being processed. "
                + "- ON_HOLD: Payment is on hold due to verification or other reasons. ");
    return generateFields(
        includeId,
        "paymentStatus",
        descPaymentStatus,
        customDescriptions,
        new String[] {"paymentStatusName"});
  }
}
