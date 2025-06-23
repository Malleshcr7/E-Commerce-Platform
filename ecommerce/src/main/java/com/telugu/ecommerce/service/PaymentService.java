package com.telugu.ecommerce.service;

import com.razorpay.PaymentLink;
import com.razorpay.RazorpayException;
import com.stripe.exception.StripeException;
import com.telugu.ecommerce.model.Order;
import com.telugu.ecommerce.model.PaymentOrder;
import com.telugu.ecommerce.model.User;

import java.util.Set;

public interface PaymentService {
    PaymentOrder createOrder(User user, Set<Order> orders);
    PaymentOrder getPaymentOrderById(Long orderid) throws Exception;
    PaymentOrder getPaymentOrderByPaymentId(String oderId);
    Boolean ProceedPayment(PaymentOrder paymentOrder,
                           String PaymentId,
                           String PaymentLinkId) throws RazorpayException;
    PaymentLink createPaymentLink(User user, Long amount, Long OrderId) throws RazorpayException;
    String createStripePaymentLink(User user, Long amount, Long OrderId) throws StripeException;
}
