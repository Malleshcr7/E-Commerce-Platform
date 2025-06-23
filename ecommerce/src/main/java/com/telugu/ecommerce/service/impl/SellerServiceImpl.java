package com.telugu.ecommerce.service.impl;

import com.telugu.ecommerce.config.JwtProvider;
import com.telugu.ecommerce.domain.AccountStatus;
import com.telugu.ecommerce.domain.USER_ROLE;
import com.telugu.ecommerce.model.Address;
import com.telugu.ecommerce.model.Seller;
import com.telugu.ecommerce.repository.AddressRepository;
import com.telugu.ecommerce.repository.SellerRepository;
import com.telugu.ecommerce.service.SellerService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class SellerServiceImpl implements SellerService {

    private final SellerRepository sellerRepository;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;
    private final AddressRepository addressRepository;

    @Override
    public Seller createSeller(Seller seller) {

        Seller sellerExist=sellerRepository.findByEmail(seller.getEmail());
        if(sellerExist!=null){
            throw new IllegalArgumentException("Seller already exist with email: " + seller.getEmail());
        }
        Address saveAddress=addressRepository.save(seller.getPickupAdress());
        Seller newSeller=new Seller();
        newSeller.setEmail(seller.getEmail());
        newSeller.setPassword(passwordEncoder.encode(seller.getPassword()));
        newSeller.setSellarName(seller.getSellarName());
        newSeller.setMobile(seller.getMobile());
        newSeller.setPickupAdress(saveAddress);
        newSeller.setRole(USER_ROLE.ROLE_SELLER);
        newSeller.setAccountStatus(AccountStatus.PENDING_VERIFICATION);
        newSeller.setGSTIN(seller.getGSTIN());
        newSeller.setBusinessDetails(seller.getBusinessDetails());
        newSeller.setBankDetails(seller.getBankDetails());
        return sellerRepository.save(newSeller);
    }

    @Override
    public Seller getSellerById(Long id) {
        return sellerRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Seller not found with id: " + id));
    }


    @Override
    public Seller getSellerProfile(String jwt) throws Exception {
        // Decode the JWT token to extract seller's email
        String email = jwtProvider.getEmailFromJwtToken(jwt);

        // Fetch the seller by email from the repository
        Seller seller = sellerRepository.findByEmail(email);

        if (seller == null) {
            throw new IllegalArgumentException("Seller not found");
        }

        return seller;
    }


        @Override
    public Seller getSellerByEmail(String email) {
        Seller seller = sellerRepository.findByEmail(email);
        if(seller==null){
            throw new IllegalArgumentException("Seller not found with email: " + email);
        }
        return null;
    }

    @Override
    public List<Seller> getAllSellers(AccountStatus status) throws Exception {
        return sellerRepository.findByAccountStatus(status);
    }

    @Override
    public Seller updateSeller(Long id, Seller seller) throws Exception {
        Seller updateSeller=sellerRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Seller not found with id: " + id));
        if(seller.getSellarName()!=null){
            updateSeller.setSellarName(seller.getSellarName());
        }
        if(seller.getMobile()!=null){
            updateSeller.setMobile(seller.getMobile());
        }
        if(seller.getPickupAdress()!=null){
            updateSeller.setPickupAdress(seller.getPickupAdress());
        }
        if(seller.getGSTIN()!=null){
            updateSeller.setGSTIN(seller.getGSTIN());
        }
        if(seller.getBusinessDetails()!=null){
            updateSeller.setBusinessDetails(seller.getBusinessDetails());
        }
        if(seller.getBankDetails()!=null){
            updateSeller.setBankDetails(seller.getBankDetails());
        }
        return sellerRepository.save(updateSeller);

    }

    @Override
    public void deleteSeller(Long id) {
        Seller seller=getSellerById(id);
        sellerRepository.delete(seller);

    }

    @Override
    public Seller verifyEmail(String email, String otp) throws Exception {
        Seller seller=getSellerByEmail(email);
        seller.setEmailVerified(true);

        return sellerRepository.save(seller);
    }

    @Override
    public Seller updateSellerStatus(Long id, AccountStatus status) throws Exception {
        Seller seller=getSellerById(id);
        seller.setAccountStatus(status);

        return sellerRepository.save(seller);

    }
}
