package com.telugu.ecommerce.service.impl;

import com.telugu.ecommerce.model.Deal;
import com.telugu.ecommerce.model.Home;
import com.telugu.ecommerce.model.HomeCategory;
import com.telugu.ecommerce.domain.HomeCategorySection;
import com.telugu.ecommerce.repository.DealRepository;
import com.telugu.ecommerce.service.HomeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HomeServiceImpl implements HomeService {

    private final DealRepository dealRepository;

    @Override
    public Home createHomePageData(List<HomeCategory> allCategories) {
        List<HomeCategory> grid = allCategories.stream()
                .filter(category -> category.getSection() == HomeCategorySection.GRID)
                .collect(Collectors.toList());

        List<HomeCategory> shopByCategories = allCategories.stream()
                .filter(category -> category.getSection() == HomeCategorySection.SHOP_BY_CATEGORY)
                .collect(Collectors.toList());

        List<HomeCategory> electronicCategories = allCategories.stream()
                .filter(category -> category.getSection() == HomeCategorySection.ELECTRONIC_CATEGORY)
                .collect(Collectors.toList());

        List<HomeCategory> dealCategories = allCategories.stream()
                .filter(category -> category.getSection() == HomeCategorySection.DEALS)
                .collect(Collectors.toList());

        List<Deal> createdDeals = new ArrayList<>();
        if (dealRepository.findAll().isEmpty()) {
            List<Deal> deals = allCategories.stream()
                    .filter(category -> category.getSection() == HomeCategorySection.DEALS)
                    .map(category -> new Deal(null, 10, category))
                    .collect(Collectors.toList());
            createdDeals = dealRepository.saveAll(deals);
        } else {
            createdDeals = dealRepository.findAll();
}

            Home home = new Home();
            home.setGrid(grid);
            home.setShopByCategories(shopByCategories);
            home.setElectronicCategories(electronicCategories);
            home.setDealCategories(dealCategories);
            home.setDeals(createdDeals);

            return home;
        }


    // Example method to fetch deals, replace with actual implementation
    private List<Deal> getDeals() {
        // Your logic to fetch deals
        return List.of(); // Placeholder
    }
}
