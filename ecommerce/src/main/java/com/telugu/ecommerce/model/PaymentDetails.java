package com.telugu.ecommerce.model;


import com.telugu.ecommerce.domain.PaymentStatus;
import lombok.Data;

@Data
public class PaymentDetails {
    private String paymentId;
    private String razorpayPaymentLinkId;
    private String razorPaymentLinkReferenceId;
    private String razorPaymentLinkStatus;
    private String razorPaymentLinkIdZWSP;
    private PaymentStatus status;
}
