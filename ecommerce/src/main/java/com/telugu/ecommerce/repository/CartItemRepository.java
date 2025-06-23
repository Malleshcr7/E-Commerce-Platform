package com.telugu.ecommerce.repository;

import com.telugu.ecommerce.model.CartItem;
import com.telugu.ecommerce.model.Cart;
import com.telugu.ecommerce.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    CartItem findByCartAndProductAndSize(Cart cart, Product product, int size);
}
