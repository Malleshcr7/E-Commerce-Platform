package com.telugu.ecommerce.service;

import com.telugu.ecommerce.model.Product;
import com.telugu.ecommerce.model.Seller;
import com.telugu.ecommerce.request.CreateProductRequest;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {

    Product createProduct(CreateProductRequest req, Seller seller);

    void deleteProduct(Long productId);

    Product updateProduct(Long productId, Product product);

    Product findProductById(Long productId);

    List<Product> searchProducts(String query);

    Page<Product> getAllProducts(String category, String brand, String colors, String sizes, Integer minPrice, Integer maxPrice, Integer minDiscount, String sort, String stock, Integer pageNumber, String query);

    List<Product> getProductsBySellerId(Long sellerId);

    Product applyDiscount(Long productId, int discountPercentage) throws Exception;
}
