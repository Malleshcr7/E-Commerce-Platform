package com.telugu.ecommerce.controller;


import com.telugu.ecommerce.domain.USER_ROLE;
import com.telugu.ecommerce.model.User;
import com.telugu.ecommerce.model.VerificationCode;
import com.telugu.ecommerce.repository.UserRepository;
import com.telugu.ecommerce.request.LoginOtpRequest;
import com.telugu.ecommerce.request.LoginRequest;
import com.telugu.ecommerce.response.ApiResponse;
import com.telugu.ecommerce.response.AuthResponse;
import com.telugu.ecommerce.response.SignupRequest;
import com.telugu.ecommerce.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailSendException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository userRepository;
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse>createOtpHandler(@RequestBody SignupRequest req) throws InterruptedException, Exception {
       String jwt= authService.createUser(req);

        AuthResponse res=new AuthResponse();
        res.setJwt(jwt);
        res.setMessage("User created successfully");
        res.setRole(USER_ROLE.ROLE_CUSTOMER);

        return ResponseEntity.ok(res);
    }
    @PostMapping("/send/login-signup-otp")
    public ResponseEntity<ApiResponse> sendOtpHandler(@RequestBody LoginOtpRequest req) {
        ApiResponse res = new ApiResponse();
        try {
            authService.sendLoginOtp(req.getEmail(), req.getRole());
            res.setMessage("OTP sent successfully");
            return ResponseEntity.ok(res);
        } catch (MailSendException e) {
            res.setMessage("Failed to send OTP: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(res);
        } catch (Exception e) {
            res.setMessage("An unexpected error occurred: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(res);
        }
    }

    @PostMapping("/signing_")
    public ResponseEntity<AuthResponse> loginHandler(@RequestBody LoginRequest req) throws Exception {
        AuthResponse authResponse=authService.signing (req);



        return ResponseEntity.ok(authResponse);
    }
}
