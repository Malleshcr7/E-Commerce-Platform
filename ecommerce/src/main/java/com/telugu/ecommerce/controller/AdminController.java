package com.telugu.ecommerce.controller;

import com.telugu.ecommerce.domain.AccountStatus;
import com.telugu.ecommerce.model.Seller;
import com.telugu.ecommerce.service.SellerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class AdminController {

    private final SellerService sellerService;

     @PatchMapping("/seller/{id}/status/{status}")
    public ResponseEntity<Seller> updateSellerStatus(@PathVariable Long id, @PathVariable AccountStatus status) throws Exception {
         Seller updateSeller = sellerService.updateSellerStatus(id, status);
         return ResponseEntity.ok(updateSeller);

    }
}
