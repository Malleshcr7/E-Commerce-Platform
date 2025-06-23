package com.telugu.ecommerce.controller;

import com.telugu.ecommerce.domain.OrderStatus;
import com.telugu.ecommerce.model.Order;
import com.telugu.ecommerce.model.Seller;
import com.telugu.ecommerce.service.OrderService;
import com.telugu.ecommerce.service.SellerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/seller/order")
public class SellerOrderController {

    private final OrderService orderService;
    private final SellerService sellerService;

    @GetMapping()
    public ResponseEntity<List<Order>> getSellerOrders(@RequestHeader("Authorization") String jwt) throws Exception {
        Seller seller = sellerService.getSellerProfile(jwt);
        List<Order> orders = orderService.sellersOrder(seller.getId());
        return new ResponseEntity<>(orders, HttpStatus.ACCEPTED);

    }

    @PatchMapping("{orderId}/status/{orderStatus}")
    public ResponseEntity<Order> updateOrderStatus(@PathVariable Long orderId,
                                                   @PathVariable OrderStatus orderStatus,
                                                   @RequestHeader("Authorization") String jwt) throws Exception {
        Order updatedOrder = orderService.updateOrderStatus(orderId, orderStatus);
        return new ResponseEntity<>(updatedOrder, HttpStatus.OK);
    }


}
