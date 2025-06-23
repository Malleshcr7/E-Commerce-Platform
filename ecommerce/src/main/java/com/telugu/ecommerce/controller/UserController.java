package com.telugu.ecommerce.controller;


import com.telugu.ecommerce.model.User;
import com.telugu.ecommerce.model.VerificationCode;
import com.telugu.ecommerce.response.ApiResponse;
import com.telugu.ecommerce.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/users/profile")
    public ResponseEntity<User> sendOtpHandler(@RequestHeader("Authorization") String jwt) throws Exception {
        User user=userService.findUserByJwtToken(jwt);


        return ResponseEntity.ok(user);
    }

}
