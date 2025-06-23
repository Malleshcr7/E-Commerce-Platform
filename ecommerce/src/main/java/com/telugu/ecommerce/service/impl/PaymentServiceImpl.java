package com.telugu.ecommerce.service.impl;

import com.razorpay.Payment;
import com.razorpay.PaymentLink;
import com.razorpay.RazorpayException;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import com.telugu.ecommerce.domain.PaymentOrderStatus;
import com.telugu.ecommerce.domain.PaymentStatus;
import com.telugu.ecommerce.model.Order;
import com.telugu.ecommerce.model.PaymentOrder;
import com.telugu.ecommerce.model.User;
import com.telugu.ecommerce.repository.OrderRepository;
import com.telugu.ecommerce.repository.PaymentOrderRepository;
import com.telugu.ecommerce.service.PaymentService;
import com.razorpay.RazorpayClient;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private PaymentOrderRepository paymentOrderRepository;
    private OrderRepository orderRepository;

    private String apiKey="apiKey";
    private String apiSecret="apiSecret";

    private String stripeSecretKey = "stripeSecretKey";

    @Override
    public PaymentOrder createOrder(User user, Set<Order> orders) {
        Long amount =orders.stream().mapToLong(Order::getTotalsellingPrice).sum();

        PaymentOrder paymentOrder = new PaymentOrder();
        paymentOrder.setUser(user);
        paymentOrder.setAmount(amount);
        paymentOrder.setPaymentOrderStatus(PaymentOrderStatus.PENDING);
        paymentOrder.setOrders(orders);
        paymentOrderRepository.save(paymentOrder);
        return null;
    }

    @Override
    public PaymentOrder getPaymentOrderById(Long orderid) throws Exception {

        return paymentOrderRepository.findById(orderid)
                .orElseThrow(() -> new Exception("PaymentOrder not found "));

    }

    @Override
    public PaymentOrder getPaymentOrderByPaymentId(String oderId) {
        PaymentOrder paymentOrder = paymentOrderRepository.findByPaymentLinkId(oderId);

        if (paymentOrder == null) {
            throw new RuntimeException("PaymentOrder not found");
        }
        return paymentOrder;
    }

    @Override
    public Boolean ProceedPayment(PaymentOrder paymentOrder, String PaymentId, String PaymentLinkId) throws RazorpayException {
        if(paymentOrder.getPaymentOrderStatus().equals(PaymentOrderStatus.PENDING)) {
            RazorpayClient rzp = new RazorpayClient(apiKey, apiSecret);
            Payment payment = rzp.payments.fetch(PaymentId);

            String status = payment.get("status");
            if (status.equals("APPROVED")) {
                Set<Order> orders = paymentOrder.getOrders();
                for(Order order : orders) {
                    order.setPaymentStatus(PaymentStatus.COMPLETED);
                    orderRepository.save(order);
                }
                    paymentOrder.setPaymentOrderStatus(PaymentOrderStatus.SUCCESS);
                    paymentOrderRepository.save(paymentOrder);
                return true;
            }

            paymentOrder.setPaymentOrderStatus(PaymentOrderStatus.FAILED);
            paymentOrderRepository.save(paymentOrder);
            return false;
        }
        return false;
    }

    @Override
    public PaymentLink createPaymentLink(User user, Long amount, Long OrderId) throws RazorpayException {
        amount = amount * 100;

        try {
            RazorpayClient rzp = new RazorpayClient(apiKey, apiSecret);

            JSONObject paymentLinkRequest = new JSONObject();
            paymentLinkRequest.put("amount", amount);
            paymentLinkRequest.put("currency", "INR");
            paymentLinkRequest.put("description", "Payment for Order Id: " + OrderId);

            JSONObject customer = new JSONObject();
            customer.put("name", user.getFullName());
            customer.put("email", user.getEmail());
            paymentLinkRequest.put("customer", customer);

            JSONObject notify = new JSONObject();
            //notify.put("sms", true);
            notify.put("email", true);
            paymentLinkRequest.put("notify", notify);

            paymentLinkRequest.put("callback_url","http://localhost:3000/payment-success" + OrderId);
            paymentLinkRequest.put("callback_method","get");


            PaymentLink paymentLink = rzp.paymentLink.create(paymentLinkRequest);

            String paymentLinkUrl = paymentLink.get("short_url");
            String paymentLinkId = paymentLink.get("id");

            return paymentLink;
        }

        catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RazorpayException(e.getMessage());
        }

    }

    @Override
    public String createStripePaymentLink(User user, Long amount, Long OrderId) throws StripeException {
        Stripe.apiKey=stripeSecretKey;

        SessionCreateParams params = SessionCreateParams.builder()
                .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl("http://localhost:3000/payment-success" + OrderId)
                .setCancelUrl("http://localhost:3000/payment-cancel")
                .addLineItem(SessionCreateParams.LineItem.builder()
                                .setQuantity(1L)
                                .setPriceData(SessionCreateParams.LineItem.PriceData.builder()
                                        .setCurrency("usd")
                                        .setUnitAmount(amount*100)
                                        .setProductData(
                                                SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                        .setName("MALLESH'S E-COMMERCE Payment" )
                                                        .build()
                                                ).build()
                                ).build()
                ).build();

        Session session=Session.create(params);
        return session.getUrl();
        
    }
}
