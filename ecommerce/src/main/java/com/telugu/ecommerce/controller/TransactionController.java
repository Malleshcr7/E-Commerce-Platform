package com.telugu.ecommerce.controller;

import com.telugu.ecommerce.model.Seller;
import com.telugu.ecommerce.model.Transaction;
import com.telugu.ecommerce.service.SellerService;
import com.telugu.ecommerce.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/transaction")
public class TransactionController {

    private final TransactionService transactionService;
    private final SellerService sellerService;

    @GetMapping("/seler")
    public ResponseEntity<List<Transaction>> getTransactionBySellerId(@RequestHeader("Authorization") String jwt)throws Exception{
        Seller seller=sellerService.getSellerProfile(jwt);
        List<Transaction> transactions=transactionService.getTransactionBySellerId(seller);
        return ResponseEntity.ok(transactions);
    }

    @GetMapping
    public ResponseEntity<List<Transaction>> getAllTransaction(){
        List<Transaction> transactions=transactionService.getAllTransactions();
        return ResponseEntity.ok(transactions);
    }

}

