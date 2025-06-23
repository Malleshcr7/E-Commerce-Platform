package com.telugu.ecommerce.controller;


import com.telugu.ecommerce.domain.AccountStatus;
import com.telugu.ecommerce.model.Seller;
import com.telugu.ecommerce.model.SellerReport;
import com.telugu.ecommerce.model.VerificationCode;
import com.telugu.ecommerce.repository.VerificationCodeRepository;
import com.telugu.ecommerce.request.LoginRequest;
import com.telugu.ecommerce.response.AuthResponse;
import com.telugu.ecommerce.service.AuthService;
import com.telugu.ecommerce.service.EmailService;
import com.telugu.ecommerce.service.SellerReportService;
import com.telugu.ecommerce.service.SellerService;
import com.telugu.ecommerce.utils.OtpUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sellers")
public class SellerController {
    private final SellerService sellerService;
    private final VerificationCodeRepository verificationCodeRepository;
    private final AuthService authService;
    private final EmailService emailService;
    private final SellerReportService sellerReportService;


    @PostMapping("/login")
    public ResponseEntity<AuthResponse> LoginSeller(
            @RequestBody LoginRequest req
            ) throws Exception {
        String otp=req.getOtp();
        String email= req.getEmail();


        req.setEmail("seller_"+email);
        AuthResponse authResponse=authService.signing(req);
                return ResponseEntity.ok(authResponse);
    }

    @PostMapping
    public ResponseEntity<Seller> createSeller(@RequestBody Seller seller) throws Exception, MessagingException {
        Seller savedSeller = sellerService.createSeller(seller);

        String otp = OtpUtil.generateOtp();

        VerificationCode verificationCode = new VerificationCode();
        verificationCode.setOtp(otp);
        verificationCode.setEmail(seller.getEmail());
        verificationCodeRepository.save(verificationCode);
        String subject = "Email Verification";
        String text = "Welcome to Ecommerce. Your OTP is: <b>" + otp + "</b><br>Or click the link to verify: ";
        String link = "http://localhost:8080/sellers/verify/" + otp;

        emailService.sendVerificationOtpEmail(seller.getEmail(), verificationCode.getOtp(), subject, text + link);
        return new ResponseEntity<>(savedSeller, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Seller> getSellerById(@PathVariable Long id)  {
        Seller seller = sellerService.getSellerById(id);
        return new ResponseEntity<>(seller, HttpStatus.OK);
    }

    @GetMapping("/profile")
    public ResponseEntity<Seller> getSellerProfile(@RequestHeader("Authorization") String jwt) throws Exception  {


        Seller seller = sellerService.getSellerProfile(jwt);
        return new ResponseEntity<>(seller, HttpStatus.OK);

    }

    @GetMapping("/report")
    public ResponseEntity<SellerReport> getSellerReport(@RequestHeader("Authorization") String jwt) throws Exception  {

        Seller seller = sellerService.getSellerProfile(jwt);
        SellerReport report = sellerReportService.getSellerReport(seller);
        return new ResponseEntity<>(report, HttpStatus.OK);
    }


    @GetMapping
    public ResponseEntity<List<Seller>> getAllSellers(@RequestParam(required = false) AccountStatus status) throws Exception {

        List<Seller> sellers = sellerService.getAllSellers(status);
        return ResponseEntity.ok(sellers);
    }

    @PatchMapping()
    public ResponseEntity<Seller> updateSeller(@RequestHeader("Authorization") String jwt,@RequestBody Seller seller) throws Exception {
        Seller profile = sellerService.getSellerProfile(jwt);
        Seller updatedSeller = sellerService.updateSeller(profile.getId(), seller);
        return ResponseEntity.ok(updatedSeller);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSeller(@PathVariable Long id) {
        sellerService.deleteSeller(id);
        return ResponseEntity.noContent().build();
    }





}
