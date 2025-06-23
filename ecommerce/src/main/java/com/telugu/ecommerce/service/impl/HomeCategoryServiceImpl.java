package com.telugu.ecommerce.service.impl;

import com.telugu.ecommerce.model.HomeCategory;
import com.telugu.ecommerce.domain.HomeCategorySection;
import com.telugu.ecommerce.repository.HomeCategoryRepository;
import com.telugu.ecommerce.service.HomeCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HomeCategoryServiceImpl implements HomeCategoryService {

    private final HomeCategoryRepository homeCategoryRepository;

    @Override
    public List<HomeCategory> getAllHomeCategories() {
        return homeCategoryRepository.findAll();
    }

    @Override
    public List<HomeCategory> createCategories(List<HomeCategory> homeCategories) {
        if (homeCategoryRepository.findAll().isEmpty()){

            return homeCategoryRepository.saveAll(homeCategories);
        }
        return homeCategoryRepository.findAll();
    }

    @Override
    @PreAuthorize("hasAuthority('ADMIN')")
    public HomeCategory createHomeCategory(HomeCategory homeCategory) {
        return homeCategoryRepository.save(homeCategory);
    }

    @Override
    @PreAuthorize("hasAuthority('ADMIN')")
    public HomeCategory updateHomeCategory(Long id, HomeCategory homeCategoryDetails) {
        HomeCategory existingHomeCategory = homeCategoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("HomeCategory not found with id: " + id));

        existingHomeCategory.setName(homeCategoryDetails.getName());
        existingHomeCategory.setImage(homeCategoryDetails.getImage());
        existingHomeCategory.setCategoryId(homeCategoryDetails.getCategoryId());
        existingHomeCategory.setSection(homeCategoryDetails.getSection());

        return homeCategoryRepository.save(existingHomeCategory);
    }

    @Override
    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteHomeCategory(Long id) {
        HomeCategory existingHomeCategory = homeCategoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("HomeCategory not found with id: " + id));
        homeCategoryRepository.delete(existingHomeCategory);
    }
}
