package com.telugu.ecommerce.service;

import com.telugu.ecommerce.domain.USER_ROLE;
import com.telugu.ecommerce.request.LoginRequest;
import com.telugu.ecommerce.response.AuthResponse;
import com.telugu.ecommerce.response.SignupRequest;

public interface AuthService {

    void sendLoginOtp(String email, USER_ROLE role) throws Exception;
    String createUser(SignupRequest req) throws Exception;
    AuthResponse signing(LoginRequest req);
}
