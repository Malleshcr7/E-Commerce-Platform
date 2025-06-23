package com.telugu.ecommerce.service.impl;

import com.telugu.ecommerce.model.Cart;
import com.telugu.ecommerce.model.CartItem;
import com.telugu.ecommerce.model.Product;
import com.telugu.ecommerce.model.User;
import com.telugu.ecommerce.repository.CartItemRepository;
import com.telugu.ecommerce.repository.CartRepository;
import com.telugu.ecommerce.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;

    @Override
    public CartItem addCartItem(User user, Product product, int size, int quantity) {
        Cart cart = findUserCart(user);

        CartItem isPresent = cartItemRepository.findByCartAndProductAndSize(cart, product, size);

        if (isPresent == null) {
            CartItem cartItem = new CartItem();
            cartItem.setProduct(product);
            cartItem.setQuantity(quantity);
            cartItem.setUserId(user.getId());
            cartItem.setSize(size);

            int totalPrice = quantity * product.getSellingPrice();
            cartItem.setSellingPrice(totalPrice);
            cartItem.setMrpPrice(quantity * product.getMrpPrice());

            cart.getCartItems().add(cartItem);
            cartItem.setCart(cart);

            return cartItemRepository.save(cartItem);
        }

        return isPresent;
    }


    @Override
    public Cart findUserCart(User user) {
        Cart cart = cartRepository.findByUserId(user.getId()).orElseThrow();

        int totalPrice = 0;
        int totalDiscountedPrice = 0;
        int totalItem = 0;

        // Existing part where you calculate total prices in findUserCart method
        for (CartItem item : cart.getCartItems()) {
            int mrpPrice = item.getMrpPrice() != null ? item.getMrpPrice() : 0;
            int sellingPrice = item.getSellingPrice() != null ? item.getSellingPrice() : 0;
            totalPrice += mrpPrice;
            totalDiscountedPrice += sellingPrice;
            totalItem += item.getQuantity();
        }


        cart.setTotalMrpPrice(totalPrice);
        cart.setTotalSellingPrice(totalDiscountedPrice);
        cart.setTotalitem(totalItem);
        cart.setDiscountPercentage(String.valueOf(calculateDiscountPercentage(totalPrice, totalDiscountedPrice)));

        return cart;
    }

    private int calculateDiscountPercentage(int mrpPrice, int sellingPrice) {
        if (mrpPrice <= 0) {
            return 0;
        }
        double discount = mrpPrice - sellingPrice;
        double discountPercentage = (discount / mrpPrice) * 100;
        return (int) discountPercentage;
    }
}
