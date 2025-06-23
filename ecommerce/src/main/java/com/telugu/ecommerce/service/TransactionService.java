package com.telugu.ecommerce.service;

import com.telugu.ecommerce.model.Order;
import com.telugu.ecommerce.model.Seller;
import com.telugu.ecommerce.model.Transaction;

import java.util.List;

public interface TransactionService {

    Transaction crateTransaction(Order order);
    List<Transaction> getTransactionBySellerId(Seller seller);
    List<Transaction> getAllTransactions();
}
