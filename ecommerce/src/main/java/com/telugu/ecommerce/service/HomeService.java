package com.telugu.ecommerce.service;

import com.telugu.ecommerce.model.Home;
import com.telugu.ecommerce.model.HomeCategory;

import java.util.List;

public interface HomeService {
    public Home createHomePageData(List<HomeCategory> allCategories);
}
