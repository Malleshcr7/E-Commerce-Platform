package com.telugu.ecommerce.repository;

import com.telugu.ecommerce.model.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WishlistRepository extends JpaRepository<Wishlist, Long> {

    Wishlist findByUserId(Long userId);
}
