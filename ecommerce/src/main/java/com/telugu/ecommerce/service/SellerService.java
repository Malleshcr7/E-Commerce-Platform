package com.telugu.ecommerce.service;

import com.telugu.ecommerce.domain.AccountStatus;
import com.telugu.ecommerce.model.Seller;

import java.util.List;

public interface SellerService {

    Seller getSellerProfile(String jwt) throws Exception;
    Seller createSeller(Seller seller);
    Seller getSellerById(Long id);
    Seller getSellerByEmail(String email);
    List<Seller> getAllSellers(AccountStatus status) throws Exception;
    Seller updateSeller(Long id, Seller seller) throws Exception;
    void deleteSeller(Long id);
    Seller verifyEmail(String email, String otp) throws Exception;
    Seller updateSellerStatus(Long id, AccountStatus status) throws Exception;
}
