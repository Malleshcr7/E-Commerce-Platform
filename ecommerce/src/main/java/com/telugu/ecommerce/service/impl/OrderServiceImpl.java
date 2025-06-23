package com.telugu.ecommerce.service.impl;

import com.telugu.ecommerce.domain.PaymentStatus;
import com.telugu.ecommerce.model.*;
import com.telugu.ecommerce.domain.OrderStatus;
import com.telugu.ecommerce.repository.OrderRepository;
import com.telugu.ecommerce.repository.OrderItemRepository;
import com.telugu.ecommerce.repository.AddressRepository;
import com.telugu.ecommerce.repository.ProductRepository;
import com.telugu.ecommerce.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final AddressRepository addressRepository;




    @Override
    public Set<Order> createOrder(User user, Address shippingAddress, Cart cart) {
        if (!user.getAddresses().contains(shippingAddress)) {
            user.getAddresses().add(shippingAddress);
        }
        Address address = addressRepository.save(shippingAddress);

        // Grouping items by seller
        Map<Long, List<CartItem>> itemsBySeller = cart.getCartItems().stream()
                .collect(Collectors.groupingBy(item -> item.getProduct().getSeller().getId()));

        Set<Order> orders = new HashSet<>();

        for (Map.Entry<Long, List<CartItem>> entry : itemsBySeller.entrySet()) {
            Long sellerId = entry.getKey();
            List<CartItem> items = entry.getValue();

            // Calculate totals
            int totalMrpPrice = items.stream().mapToInt(item -> item.getMrpPrice() * item.getQuantity()).sum();
            int totalSellingPrice = items.stream().mapToInt(item -> item.getSellingPrice() * item.getQuantity()).sum();
            int totalItem = items.stream().mapToInt(CartItem::getQuantity).sum();

            // Create Order
            Order createdOrder = new Order();
            createdOrder.setUser(user);
            createdOrder.setSellerId(sellerId);
            createdOrder.setTotalmrpPrice(totalMrpPrice);
            createdOrder.setTotalsellingPrice(totalSellingPrice);
            createdOrder.setTotalitem(totalItem);
            createdOrder.setShippingAddress(address);
            createdOrder.setOrderStatus(OrderStatus.PENDING);
            createdOrder.setPaymentStatus(PaymentStatus.PENDING);

            // Populate OrderItems
            List<OrderItem> orderItems = new ArrayList<>();
            for (CartItem cartItem : items) {
                OrderItem orderItem = new OrderItem();
                orderItem.setOrder(createdOrder);
                orderItem.setProduct(cartItem.getProduct());
                orderItem.setQuantity(cartItem.getQuantity());
                orderItem.setSize(String.valueOf(cartItem.getSize()));
                orderItem.setSellingPrice(cartItem.getSellingPrice());
                orderItem.setMrpPrice(cartItem.getMrpPrice());
                orderItems.add(orderItem);
            }
            createdOrder.setOrderItems(orderItems);

            // Save Order and OrderItems
            Order savedOrder = orderRepository.save(createdOrder);
            orderItemRepository.saveAll(orderItems);
            orders.add(savedOrder);
        }

        return orders;
    }


    @Override
    public Order findOrderById(long id) throws Exception {
        return orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Order not found with id: " + id));
    }

    @Override
    public List<Order> usersOrderHistory(Long userId) {
        return orderRepository.findByUserId(userId);
    }

    @Override
    public List<Order> sellersOrder(Long sellerId) {
        return orderRepository.findBySellerId(sellerId);
    }



    @Override
    public Order updateOrderStatus(Long orderId, OrderStatus orderStatus) throws Exception {
        Order order = findOrderById(orderId);

        // Logging the status change
        Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);
        logger.info("Updating order {} status from {} to {}", orderId, order.getOrderStatus(), orderStatus);

        // Validate status transition (example: PENDING -> SHIPPED -> DELIVERED)
        validateStatusTransition(order.getOrderStatus(), orderStatus);

        // Update status
        order.setOrderStatus(orderStatus);

        // Notify user and seller
        notifyUser(order.getUser(), orderStatus);
        notifySeller(order.getSellerId(), orderStatus);

        return orderRepository.save(order);
    }

    private void validateStatusTransition(OrderStatus currentStatus, OrderStatus newStatus) throws Exception {
        // Implement validation logic for order status transitions
        // Example: if(currentStatus == OrderStatus.DELIVERED && newStatus == OrderStatus.SHIPPED) { throw new Exception("Invalid transition"); }
    }

    private void notifyUser(User user, OrderStatus orderStatus) {
        // Implement logic to notify the user about the order status update
        // Example: email, SMS, push notification
    }

    private void notifySeller(Long sellerId, OrderStatus orderStatus) {
        // Implement logic to notify the seller about the order status update
        // Example: email, SMS, push notification
    }


    @Override
    public Order cancelOrder(Long orderId, User user) throws Exception {
        Order order = findOrderById(orderId);

        // Ensure the user is authorized to cancel the order
        if (!order.getUser().getId().equals(user.getId())) {
            throw new Exception("Unauthorized: You can only cancel your own orders.");
        }


        order.setOrderStatus(OrderStatus.CANCELLED);

        // Optionally, add more logic here for refunds, inventory updates, etc.

        // Save the updated order
        return orderRepository.save(order);
    }


    @Override
    public OrderItem findOrderItemById(Long orderItemId) {
        return orderItemRepository.findById(orderItemId)
                .orElseThrow(() -> new IllegalArgumentException("OrderItem not found with id: " + orderItemId));
    }

    @Override
    public Address findAddressById(Long addressId) {
        return addressRepository.findById(addressId)
                .orElseThrow(() -> new IllegalArgumentException("Address not found with id: " + addressId));
    }
}
