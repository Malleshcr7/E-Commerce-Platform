package com.telugu.ecommerce.service;

import com.telugu.ecommerce.model.CartItem;
import com.telugu.ecommerce.model.Cart;
import com.telugu.ecommerce.model.Product;

public interface CartItemService {

    void removeCartItem(Long userId,Long cartItemId);

    CartItem updateCartItem(Long userId, Long id, CartItem cartItem);

    CartItem findCartItemById(Long cartItemId);
}
