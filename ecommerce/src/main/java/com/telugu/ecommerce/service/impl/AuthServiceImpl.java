package com.telugu.ecommerce.service.impl;

import com.telugu.ecommerce.config.JwtProvider;
import com.telugu.ecommerce.domain.USER_ROLE;
import com.telugu.ecommerce.model.Cart;
import com.telugu.ecommerce.model.Seller;
import com.telugu.ecommerce.model.User;
import com.telugu.ecommerce.model.VerificationCode;
import com.telugu.ecommerce.repository.CartRepository;
import com.telugu.ecommerce.repository.SellerRepository;
import com.telugu.ecommerce.repository.UserRepository;
import com.telugu.ecommerce.repository.VerificationCodeRepository;
import com.telugu.ecommerce.request.LoginRequest;
import com.telugu.ecommerce.response.AuthResponse;
import com.telugu.ecommerce.response.SignupRequest;
import com.telugu.ecommerce.service.AuthService;
import com.telugu.ecommerce.service.EmailService;
import com.telugu.ecommerce.utils.OtpUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final CartRepository cartRepository;
    private final JwtProvider JwtProvider;
    private final VerificationCodeRepository verificationCodeRepository;
    private final EmailService emailService;
    private final CustomUserServiceImpl customUserService;
    private final SellerRepository sellerRepository;



    @Override
    public void sendLoginOtp(String email, USER_ROLE role) throws Exception {
        String SIGNING_PREFIX  = "signing_";


        if(email.startsWith(SIGNING_PREFIX)) {
            email = email.substring(SIGNING_PREFIX.length());



            if (role == USER_ROLE.ROLE_SELLER) {
                Seller seller = sellerRepository.findByEmail(email);
                if (seller == null) {
                    throw new IllegalArgumentException("Seller not found with email: " + email);
                }

            }
            else {
                System.out.println("email.........."+email);
                User user = userRepository.findByEmail(email);
                if (user == null) {
                    throw new IllegalArgumentException("User not found with email: " + email);
                }

            }

        }

        VerificationCode isExist=verificationCodeRepository.findByEmail(email);
        if(isExist!=null){
            verificationCodeRepository.delete(isExist);
        }

        String otp = OtpUtil.generateOtp();

        VerificationCode verificationCode = new VerificationCode();
        verificationCode.setOtp(otp);
        verificationCode.setEmail(email);
        verificationCodeRepository.save(verificationCode);

        String subject = "Welcome To MALLESH'S ECOMMERCE";
        String text = "Welcome to Ecommerce. Click on the link to verify your email:" ;
        String link = "http://localhost:8080/auth/signin/-->" + otp + " <--";
        emailService.sendVerificationOtpEmail(email, otp, subject, text+link);
    }

    @Override
    public String createUser(SignupRequest req) {

        VerificationCode verificationCode=verificationCodeRepository.findByEmail(req.getEmail());
        if (verificationCode == null || !verificationCode.getOtp().equals(req.getOtp())) {
            throw new IllegalArgumentException("Wrong Otp...");
        }

        User user = userRepository.findByEmail(req.getEmail());

        if(user==null){
            User createdUser =new User();
            createdUser.setEmail(req.getEmail());
            createdUser.setFullName(req.getFullName());
            createdUser.setRole(USER_ROLE.ROLE_CUSTOMER);
            createdUser.setMobileNumber(1234567890);
            createdUser.setPassword(passwordEncoder.encode(req.getOtp()));

            user=userRepository.save(createdUser);

            Cart cart = new Cart();
            cart.setUser(user);
            cartRepository.save(cart);
        }

        List<GrantedAuthority> authorities=new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(USER_ROLE.ROLE_CUSTOMER.toString()));

        Authentication authentication=new UsernamePasswordAuthenticationToken(req.getEmail(), null, authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return JwtProvider.generateToken(authentication);
    }

    @Override
    public AuthResponse signing(LoginRequest req) {

        String username=req.getEmail();
        String otp=req.getOtp();
        Authentication authentication=authenticate(username, otp);

        String token=JwtProvider.generateToken(authentication);

        AuthResponse authResponse=new AuthResponse();
        authResponse.setJwt(token);
        authResponse.setMessage("Login Successful");

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        String roleName=authorities.isEmpty()?null:authorities.iterator().next().getAuthority();

        authResponse.setRole(USER_ROLE.valueOf(roleName));
        return authResponse;
    }

    private Authentication authenticate(String username, String otp) {
       UserDetails userDetails = customUserService.loadUserByUsername(username);

        String SELLER_PREFIX = "seller_";
        if(username.startsWith(SELLER_PREFIX)) {
            username = username.substring(SELLER_PREFIX.length());
        }

       if(userDetails==null){
           throw new IllegalArgumentException("Invalid Username");
       }

       VerificationCode verificationCode=verificationCodeRepository.findByEmail(username);

       if (verificationCode == null || !verificationCode.getOtp().equals(otp)) {
           throw new IllegalArgumentException("Wrong Otp...");
       }
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}
