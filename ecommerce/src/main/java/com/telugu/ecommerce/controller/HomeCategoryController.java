package com.telugu.ecommerce.controller;

import com.telugu.ecommerce.model.Home;
import com.telugu.ecommerce.model.HomeCategory;
import com.telugu.ecommerce.domain.HomeCategorySection;
import com.telugu.ecommerce.response.ApiResponse;
import com.telugu.ecommerce.service.HomeCategoryService;
import com.telugu.ecommerce.service.HomeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/homeCategories")
@RequiredArgsConstructor
public class HomeCategoryController {

    private final HomeCategoryService homeCategoryService;
    private final HomeService homeService;

    @GetMapping("/admin/home-categories")
    public ResponseEntity<List<HomeCategory>> getAllHomeCategories() {
        List<HomeCategory> homeCategories = homeCategoryService.getAllHomeCategories();
        return ResponseEntity.ok(homeCategories);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Home> createHomeCategory(@RequestBody List<HomeCategory> homeCategory) {
    Home home = homeService.createHomePageData(homeCategory);
    return new ResponseEntity<>(home, HttpStatus.ACCEPTED);
    }

    @PostMapping("/createCategories")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<List<HomeCategory>> createCategories(@RequestBody List<HomeCategory> homeCategories) {
        List<HomeCategory> createdHomeCategories = homeCategoryService.createCategories(homeCategories);
        return new ResponseEntity<>(createdHomeCategories, HttpStatus.CREATED);
    }

    @PutMapping("/admin/update/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<HomeCategory> updateHomeCategory(@PathVariable Long id, @RequestBody HomeCategory homeCategory) {
        HomeCategory updatedHomeCategory = homeCategoryService.updateHomeCategory(id, homeCategory);
        return ResponseEntity.ok(updatedHomeCategory);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ApiResponse> deleteHomeCategory(@PathVariable Long id) {
        homeCategoryService.deleteHomeCategory(id);
        ApiResponse response = new ApiResponse();
        response.setMessage("HomeCategory deleted successfully.");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
