package com.telugu.ecommerce.controller;

import com.telugu.ecommerce.model.Cart;
import com.telugu.ecommerce.model.Coupon;
import com.telugu.ecommerce.model.User;
import com.telugu.ecommerce.response.ApiResponse;
import com.telugu.ecommerce.service.CouponService;
import com.telugu.ecommerce.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/coupons")
@RequiredArgsConstructor
public class CouponController {

    private final CouponService couponService;
    private final UserService userService;

    @PostMapping("/apply")
    public ResponseEntity<Cart> applyOrRemoveCoupon(@RequestParam String code,
                                                    @RequestParam String apply,
                                                    @RequestParam double orderValue,
                                                    @RequestHeader("Authorization") String jwt) {
        User user = userService.findUserByJwtToken(jwt);
        Cart updatedCart;

        if (apply.equals("true")) {
            updatedCart = couponService.applyCoupon(code, orderValue, user);
        } else  {
            updatedCart = couponService.removeCoupon(code, user);
        }
        return ResponseEntity.ok(updatedCart);
    }


    @PostMapping("/remove")
    public ResponseEntity<Cart> removeCoupon(@RequestParam String code,
                                             @RequestHeader("Authorization") String jwt) {
        User user = userService.findUserByJwtToken(jwt);
        Cart updatedCart = couponService.removeCoupon(code, user);
        return ResponseEntity.ok(updatedCart);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Coupon> findCouponById(@PathVariable Long id) {
        Coupon coupon = couponService.findCouponById(id);
        return ResponseEntity.ok(coupon);
    }

    @PostMapping("/admin/create")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Coupon> createCoupon(@RequestBody Coupon coupon) {
        Coupon createdCoupon = couponService.createCoupon(coupon);
        return new ResponseEntity<>(createdCoupon, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Coupon>> findAllCoupons() {
        List<Coupon> coupons = couponService.findAllCoupons();
        return ResponseEntity.ok(coupons);
    }

    @DeleteMapping("/admin/delete/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ApiResponse> deleteCoupon(@PathVariable Long id) {
        couponService.deleteCoupon(id);
        ApiResponse response = new ApiResponse();
        response.setMessage("Coupon deleted successfully.");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
