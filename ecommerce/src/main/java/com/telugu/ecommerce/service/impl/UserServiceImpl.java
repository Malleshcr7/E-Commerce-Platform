package com.telugu.ecommerce.service.impl;

import com.telugu.ecommerce.config.JwtProvider;
import com.telugu.ecommerce.model.User;
import com.telugu.ecommerce.repository.UserRepository;
import com.telugu.ecommerce.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;
    @Override
    public User findUserByJwtToken(String jwt) {

        String email=jwtProvider.getEmailFromJwtToken(jwt);

        User user=this.findUserByEmail(email);
        if(user==null){
            throw new IllegalArgumentException("User not found with email: " + email);
        }
        return user;
    }

    @Override
    public User findUserByEmail(String email) {

        User user = userRepository.findByEmail(email);
        if(user==null){
            throw new IllegalArgumentException("User not found with email: " + email);
        }
        return user;
    }


    @Override
    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + userId));
    }

}
