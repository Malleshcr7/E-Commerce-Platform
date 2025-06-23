package com.telugu.ecommerce.service;

import com.telugu.ecommerce.model.Product;
import com.telugu.ecommerce.model.User;
import com.telugu.ecommerce.model.Wishlist;

public interface WishlistService {
    Wishlist createWishlist(User user);

    Wishlist getWishlistByUserId(User user);
    Wishlist addProductToWishlist(User user, Product product);
}
