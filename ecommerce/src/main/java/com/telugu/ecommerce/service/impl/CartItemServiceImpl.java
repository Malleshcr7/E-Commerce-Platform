package com.telugu.ecommerce.service.impl;

import com.telugu.ecommerce.model.CartItem;
import com.telugu.ecommerce.model.User;
import com.telugu.ecommerce.repository.CartItemRepository;
import com.telugu.ecommerce.service.CartItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartItemServiceImpl implements CartItemService {

    private final CartItemRepository cartItemRepository;

    @Override
    public void removeCartItem(Long userId, Long cartItemId) {
        CartItem item = findCartItemById(cartItemId);
        User cartitmuser = item.getCart().getUser();
        if(cartitmuser.getId().equals(userId)){
            cartItemRepository.delete(item);
        } else {
            throw new IllegalArgumentException("U Can't Remove Cart Item");
        }
    }

    @Override
    public CartItem updateCartItem(Long userId, Long id, CartItem cartItem) {
        // Retrieve the cart item by its ID
        CartItem item = findCartItemById(id);

        // Get the user associated with this cart item
        User cartItemUser = item.getCart().getUser();

        // Check if the user ID matches the ID provided
        if (cartItemUser.getId().equals(userId)) {
            // Update the quantity of the cart item
            item.setQuantity(cartItem.getQuantity());

            // Calculate and set the MRP price based on quantity
            item.setMrpPrice(item.getQuantity() * item.getProduct().getMrpPrice());

            // Calculate and set the selling price based on quantity
            item.setSellingPrice(item.getQuantity() * item.getProduct().getSellingPrice());
            return cartItemRepository.save(item);
        }

        throw new IllegalArgumentException("U Can't Update Cart Item");
    }


    @Override
    public CartItem findCartItemById(Long cartItemId) {
        return cartItemRepository.findById(cartItemId).orElseThrow();
    }
}

