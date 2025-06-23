package com.telugu.ecommerce.service.impl;

import com.telugu.ecommerce.model.Category;
import com.telugu.ecommerce.model.Product;
import com.telugu.ecommerce.model.Seller;
import com.telugu.ecommerce.repository.CategoryRepository;
import com.telugu.ecommerce.repository.ProductRepository;
import com.telugu.ecommerce.request.CreateProductRequest;
import com.telugu.ecommerce.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public Product createProduct(CreateProductRequest req, Seller seller) {
        Product product = new Product();
        product.setTitle(req.getTitle());
        product.setDescription(req.getDescription());
        product.setMrpPrice(req.getMrpPrice());
        product.setSellingPrice(req.getSellingPrice());
        product.setColor(req.getColor());
        product.setImages(req.getImages());
        product.setSizes(req.getSizes());
        product.setSeller(seller);
        product.setCreatedAt(LocalDateTime.now());

        // Handle null category IDs
        Category category1 = categoryRepository.findByCategoryId(req.getCategory());
        if (category1 == null) {
            Category category = new Category();
            category.setCategoryId(req.getCategory());
            category.setLevel(1);
            category1 = categoryRepository.save(category);
        }

        Category category2 = categoryRepository.findByCategoryId(req.getCategory2());
        if (category2 == null) {
            Category category = new Category();
            category.setCategoryId(req.getCategory2());
            category.setLevel(2);
            category.setParentCategory(category1);
            category2 = categoryRepository.save(category);
        }

        Category category3 = categoryRepository.findByCategoryId(req.getCategory3());
        if (category3 == null) {
            Category category = new Category();
            category.setCategoryId(req.getCategory3());
            category.setLevel(3);
            category.setParentCategory(category2);
            category3 = categoryRepository.save(category);
        }

        product.setCategory1(category1);
        product.setCategory2(category2);
        product.setCategory3(category3);

        return productRepository.save(product);
    }

    @Override
    public Product applyDiscount(Long productId, int discountPercentage) throws Exception {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        if (discountPercentage > product.getMrpPrice()) {
            throw new IllegalArgumentException("Discount percentage cannot be greater than the original price");
        }
        // Calculate the new selling price after discount
        int newSellingPrice = product.getMrpPrice() - (product.getMrpPrice() * discountPercentage / 100);
        product.setSellingPrice(newSellingPrice);
        product.setDiscountPercentage(discountPercentage);

        return productRepository.save(product);
    }



    @Override
    public void deleteProduct(Long productId) {
        productRepository.deleteById(productId);
    }

    @Override
    public Product updateProduct(Long productId, Product product) {
        Product existingProduct = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        existingProduct.setTitle(product.getTitle());
        existingProduct.setDescription(product.getDescription());
        existingProduct.setMrpPrice(product.getMrpPrice());
        existingProduct.setSellingPrice(product.getSellingPrice());
        existingProduct.setColor(product.getColor());
        existingProduct.setImages(product.getImages());
        existingProduct.setSizes(product.getSizes());

        // Retrieve Category objects from repository
        Category category1 = categoryRepository.findByCategoryId(product.getCategory1().getCategoryId());
        Category category2 = categoryRepository.findByCategoryId(product.getCategory2().getCategoryId());
        Category category3 = categoryRepository.findByCategoryId(product.getCategory3().getCategoryId());

        existingProduct.setCategory1(category1);
        existingProduct.setCategory2(category2);
        existingProduct.setCategory3(category3);

        return productRepository.save(existingProduct);
    }

    @Override
    public Product findProductById(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));
    }


    @Override
    public List<Product> searchProducts(String query) {
        return productRepository.searchProducts(query);
    }


    @Override
    public Page<Product> getAllProducts(String category, String brand, String colors, String sizes, Integer minPrice, Integer maxPrice, Integer minDiscount, String sort, String stock, Integer pageNumber, String query) {
        PageRequest pageable = PageRequest.of(pageNumber, 10);
        // Implement your custom query here
        return productRepository.findAll(pageable);
    }

    @Override
    public List<Product> getProductsBySellerId(Long sellerId) {
        return productRepository.findBySellerId(sellerId);

    }
}
