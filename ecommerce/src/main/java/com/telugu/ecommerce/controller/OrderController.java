package com.telugu.ecommerce.controller;

import com.razorpay.PaymentLink;
import com.telugu.ecommerce.domain.PaymentMethod;
import com.telugu.ecommerce.model.*;
import com.telugu.ecommerce.repository.OrderItemRepository;
import com.telugu.ecommerce.repository.OrderRepository;
import com.telugu.ecommerce.domain.OrderStatus;
import com.telugu.ecommerce.repository.PaymentOrderRepository;
import com.telugu.ecommerce.response.PaymentLinkResponse;
import com.telugu.ecommerce.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final UserService userService;
    private final CartService cartService;
    private final SellerService sellerService;
    private final SellerReportService sellerReportService;
    private final PaymentService paymentService;
    private final PaymentOrderRepository paymentOrderRepository;

    // Assuming you have a payment service
    @PostMapping()
    public ResponseEntity<PaymentLinkResponse> createOrderHandler(
            @RequestBody Address shippingAddress,
            @RequestParam PaymentMethod paymentMethod,
            @RequestHeader("Authorization") String jwt)
            throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Cart cart = cartService.findUserCart(user);
        Set<Order> orders = orderService.createOrder(user, shippingAddress, cart);
        PaymentOrder paymentOrder = paymentService.createOrder(user, orders);
        PaymentLinkResponse res = new PaymentLinkResponse();

        if(paymentMethod.equals(PaymentMethod.RAZORPAY)) {
            PaymentLink payment = paymentService.createPaymentLink(user, paymentOrder.getAmount(), paymentOrder.getId());
            String paymentUrl = payment.get("ShortUrl");
            String paymentUrlId = payment.get("id");
            res.setPayment_Link_url(paymentUrl);

            paymentOrder.setPaymentLinkId(paymentUrlId);
            paymentOrderRepository.save(paymentOrder);
        }
        else {
            String paymentUrl = paymentService.createStripePaymentLink(user, paymentOrder.getAmount(), paymentOrder.getId());
            res.setPayment_Link_url(paymentUrl);
        }

        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<Order> findOrderById(@PathVariable Long orderId) throws Exception {
        Order order = orderService.findOrderById(orderId);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    // Get User's Order History
    @GetMapping("/user")
    public ResponseEntity<List<Order>> getUsersOrderHistory(@RequestHeader("Authorization") String jwt) {
        User user = userService.findUserByJwtToken(jwt);
        List<Order> orders = orderService.usersOrderHistory(user.getId());
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    // Get Seller's Orders
    @GetMapping("/seller/{sellerId}")
    public ResponseEntity<List<Order>> getSellersOrders(@PathVariable Long sellerId) {
        List<Order> orders = orderService.sellersOrder(sellerId);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    // Update Order Status
    @PutMapping("/{orderId}/status")
    public ResponseEntity<Order> updateOrderStatus(@PathVariable Long orderId,
                                                   @RequestBody OrderStatus orderStatus) throws Exception {
        Order updatedOrder = orderService.updateOrderStatus(orderId, orderStatus);
        return new ResponseEntity<>(updatedOrder, HttpStatus.OK);
    }

    // Cancel Order
    @PutMapping("/{orderId}/cancel")
    public ResponseEntity<Order> cancelOrder(@PathVariable Long orderId,
                                             @RequestHeader("Authorization") String jwt) throws Exception {

        User user = userService.findUserByJwtToken(jwt);
        Order order = orderService.cancelOrder(orderId, user);

         Seller seller = sellerService.getSellerById(order.getSellerId());
         SellerReport report = sellerReportService.getSellerReport(seller);

         report.setCancelledOrders(report.getCancelledOrders() + 1);
         report.setTotalRefunds(report.getTotalRefunds() + order.getTotalsellingPrice());
         sellerReportService.updateSellerReport(report); // Return the updated order
        return ResponseEntity.ok(order);
    }


    // Find Order Item by ID
    @GetMapping("/items/{orderItemId}")
    public ResponseEntity<OrderItem> findOrderItemById(@PathVariable Long orderItemId) {
        OrderItem orderItem = orderService.findOrderItemById(orderItemId);
        return new ResponseEntity<>(orderItem, HttpStatus.OK);
    }

    // Find Address by ID
    @GetMapping("/addresses/{addressId}")
    public ResponseEntity<Address> findAddressById(@PathVariable Long addressId) {
        Address address = orderService.findAddressById(addressId);
        return new ResponseEntity<>(address, HttpStatus.OK);
    }
}
