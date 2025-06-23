package com.telugu.ecommerce.service;

import com.telugu.ecommerce.model.User;

public interface UserService   {

     User findUserByJwtToken(String jwt);
     User findUserByEmail(String email);
     User getUserById(Long id);
}
