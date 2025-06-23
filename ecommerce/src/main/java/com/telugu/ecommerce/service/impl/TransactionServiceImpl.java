package com.telugu.ecommerce.service.impl;

import com.telugu.ecommerce.model.Order;
import com.telugu.ecommerce.model.Seller;
import com.telugu.ecommerce.model.Transaction;
import com.telugu.ecommerce.repository.SellerRepository;
import com.telugu.ecommerce.repository.TransactionRepository;
import com.telugu.ecommerce.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {


    private final SellerRepository sellerRepository;
    private final TransactionRepository transactionRepository;

    @Override
    public Transaction crateTransaction(Order order) {
        Seller seller = sellerRepository.findById(order.getSellerId()).get();

        Transaction transaction = new Transaction();
        transaction.setCustomer(order.getUser());
        transaction.setOrder(order);
        transaction.setSeller(seller);
        return transactionRepository.save(transaction);
    }

    @Override
    public List<Transaction> getTransactionBySellerId(Seller seller) {
        return transactionRepository.findBySellerId(seller.getId());

    }

    @Override
    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }
}
