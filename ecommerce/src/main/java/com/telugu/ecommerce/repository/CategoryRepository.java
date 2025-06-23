package com.telugu.ecommerce.repository;

import com.telugu.ecommerce.model.Category;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findByCategoryId(String categoryId);
}



