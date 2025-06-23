package com.telugu.ecommerce.repository;

import com.telugu.ecommerce.domain.HomeCategorySection;
import com.telugu.ecommerce.model.HomeCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HomeCategoryRepository extends JpaRepository<HomeCategory, Long> {
    List<HomeCategory> findBySection(HomeCategorySection section);
}
