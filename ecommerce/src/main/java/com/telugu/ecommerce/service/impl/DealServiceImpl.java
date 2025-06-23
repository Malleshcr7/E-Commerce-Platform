package com.telugu.ecommerce.service.impl;

import com.telugu.ecommerce.model.Deal;
import com.telugu.ecommerce.model.HomeCategory;
import com.telugu.ecommerce.repository.DealRepository;
import com.telugu.ecommerce.repository.HomeCategoryRepository;
import com.telugu.ecommerce.service.DealService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DealServiceImpl implements DealService {

    private final DealRepository dealRepository;
    private final HomeCategoryRepository homeCategoryRepository;

    @Override
    public List<Deal> getDeals() {
        return dealRepository.findAll();
    }

    @Override
    @PreAuthorize("hasAuthority('ADMIN')")
    public Deal createDeal(Deal deal) {
        HomeCategory category = homeCategoryRepository.findById(deal.getCategory().getId())
                .orElseThrow(() -> new IllegalArgumentException("Category not found with id: " + deal.getCategory().getId()));
        Deal newDeal = dealRepository.save(deal);
        newDeal.setCategory(category);
        newDeal.setDiscount(deal.getDiscount());
        return dealRepository.save(newDeal);
    }

    @Override
    @PreAuthorize("hasAuthority('ADMIN')")
    public Deal updateDeal(Deal deal) {
        Deal existingDeal = dealRepository.findById(deal.getId())
                .orElseThrow(() -> new IllegalArgumentException("Deal not found with id: " + deal.getId()));
        existingDeal.setDiscount(deal.getDiscount());
        existingDeal.setCategory(deal.getCategory());
        return dealRepository.save(existingDeal);
    }

    @Override
    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteDeal(Long id) {
        Deal existingDeal = dealRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Deal not found with id: " + id));
        dealRepository.delete(existingDeal);
    }
}
