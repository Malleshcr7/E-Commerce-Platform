package com.telugu.ecommerce.service;

import com.telugu.ecommerce.model.Address;
import com.telugu.ecommerce.model.Cart;
import com.telugu.ecommerce.model.Order;
import com.telugu.ecommerce.model.OrderItem;
import com.telugu.ecommerce.domain.OrderStatus;
import com.telugu.ecommerce.model.User;

import java.util.List;
import java.util.Set;

public interface OrderService {
    Set<Order> createOrder(User user, Address shippingAddress, Cart cart);
    Order findOrderById(long id) throws Exception;
    List<Order> usersOrderHistory(Long userId);
    List<Order> sellersOrder(Long sellerId);
    Order updateOrderStatus(Long orderId, OrderStatus orderStatus) throws Exception;
    Order cancelOrder(Long orderId, User user) throws Exception;
    OrderItem findOrderItemById(Long orderItemId);
    Address findAddressById(Long addressId);
}
