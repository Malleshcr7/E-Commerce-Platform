package com.telugu.ecommerce.service;

import com.telugu.ecommerce.model.HomeCategory;
import com.telugu.ecommerce.domain.HomeCategorySection;

import java.util.List;
import java.util.Optional;

public interface HomeCategoryService {
    List<HomeCategory> getAllHomeCategories();
    List<HomeCategory> createCategories(List<HomeCategory> homeCategories);
    HomeCategory createHomeCategory(HomeCategory homeCategory);
    HomeCategory updateHomeCategory(Long id, HomeCategory homeCategory);
    void deleteHomeCategory(Long id);
}
