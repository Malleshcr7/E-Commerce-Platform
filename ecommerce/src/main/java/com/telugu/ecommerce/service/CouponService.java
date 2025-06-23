package com.telugu.ecommerce.service;

import com.telugu.ecommerce.model.Cart;
import com.telugu.ecommerce.model.Coupon;
import com.telugu.ecommerce.model.User;

import java.util.List;
import java.util.Optional;

public interface CouponService {

    Cart applyCoupon(String code,double orderValue, User user);
    Cart removeCoupon(String code, User user);
    Coupon findCouponById(Long id);
    Coupon createCoupon(Coupon coupon);
    List<Coupon> findAllCoupons();
    void deleteCoupon(Long id);
}
