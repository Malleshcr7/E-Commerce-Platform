package com.telugu.ecommerce.service;

import com.telugu.ecommerce.model.Deal;

import java.util.List;

public interface DealService {

    List<Deal> getDeals();
    Deal createDeal(Deal deal);
    Deal updateDeal(Deal deal);
    void deleteDeal(Long id);
}
