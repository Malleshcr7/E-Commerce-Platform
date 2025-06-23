package com.telugu.ecommerce.service.impl;

import com.telugu.ecommerce.model.Cart;
import com.telugu.ecommerce.model.Coupon;
import com.telugu.ecommerce.model.User;
import com.telugu.ecommerce.repository.CartRepository;
import com.telugu.ecommerce.repository.CouponRepository;
import com.telugu.ecommerce.repository.UserRepository;
import com.telugu.ecommerce.service.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CouponServiceImpl implements CouponService {

    private final CouponRepository couponRepository;
    private final CartRepository cartRepository;
    private final UserRepository userRepository;

    @Override
    public Cart applyCoupon(String code, double orderValue, User user) {
        Coupon coupon = couponRepository.findByCode(code);

        if(coupon==null){
            throw new IllegalArgumentException("Coupon not found with code: " + code);
        }

        if (orderValue < coupon.getMinimumAmount()) {
            throw new IllegalArgumentException("Order value does not meet the minimum requirement for this coupon");
        }

        Cart cart = cartRepository.findByUserId(user.getId())
                .orElseThrow(() -> new IllegalArgumentException("Cart not found for user id: " + user.getId()));

        cartRepository.save(cart);

        if(user.getUsedCoupons().add(coupon)) {
            throw new IllegalArgumentException("Coupon already used");
        }
        if(coupon.isActive() && LocalDate.now().isAfter(coupon.getValidityStartDate()) && LocalDate.now().isBefore(coupon.getValidityEndDate())) {

            user.getUsedCoupons().add(coupon);
            userRepository.save(user);

            double discountPrice = (cart.getTotalSellingPrice()*coupon.getDiscountpercentage()/100);
            cart.setTotalSellingPrice(cart.getTotalSellingPrice()-discountPrice);
            cartRepository.save(cart);
            return cart;


        }

        throw new IllegalArgumentException("Coupon is not  Valid");
    }

    @Override
    public Cart removeCoupon(String code, User user) {
        Coupon coupon = couponRepository.findByCode(code);
        if (coupon == null) {
            throw new IllegalArgumentException("Coupon not found with code: " + code);
        }

        Cart cart = cartRepository.findByUserId(user.getId()).orElseThrow(() -> new IllegalArgumentException("Cart not found for user id: " + user.getId()));

        double discountPrice = (cart.getTotalSellingPrice()*coupon.getDiscountpercentage()/100);
        cart.setTotalSellingPrice(cart.getTotalSellingPrice()+discountPrice);
        cartRepository.save(cart);


        return cart;
    }

    @Override
    public Coupon findCouponById(Long id) {
        return couponRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Coupon not found with id: " + id));
    }

    @Override
    @PreAuthorize("hasAuthority('ADMIN')")
    public Coupon createCoupon(Coupon coupon) {
        return couponRepository.save(coupon);
    }

    @Override
    public List<Coupon> findAllCoupons() {
        return couponRepository.findAll();
    }

    @Override
    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteCoupon(Long id) {
        Coupon existingCoupon = couponRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Coupon not found with id: " + id));
        couponRepository.delete(existingCoupon);
    }
}
