package com.telugu.ecommerce.service;

import com.telugu.ecommerce.model.Cart;
import com.telugu.ecommerce.model.CartItem;
import com.telugu.ecommerce.model.Product;
import com.telugu.ecommerce.model.User;

import java.util.List;

public interface CartService {

    public CartItem addCartItem(User user, Product product, int size, int quantity);

    public Cart findUserCart(User user);

}
