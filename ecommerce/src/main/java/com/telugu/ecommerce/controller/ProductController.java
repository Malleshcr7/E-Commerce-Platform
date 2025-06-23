package com.telugu.ecommerce.controller;

import com.telugu.ecommerce.model.Product;
import com.telugu.ecommerce.model.Seller;
import com.telugu.ecommerce.request.CreateProductRequest;
import com.telugu.ecommerce.service.ProductService;
import com.telugu.ecommerce.service.SellerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;
    @Autowired
    private SellerService sellerService;

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody CreateProductRequest req,@RequestParam Long sellerId,@RequestHeader("Authorization") String jwt) {
        try {
            // Assume sellerService.getSellerById is available to fetch the seller by ID
            Seller seller = sellerService.getSellerById(sellerId);
            Product product = productService.createProduct(req, seller);
            return new ResponseEntity<>(product, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/{productId}/discount")
    public ResponseEntity<Product> applyDiscount(@PathVariable Long productId, @RequestParam int discountPercentage) {
        try {
            Product product = productService.applyDiscount(productId, discountPercentage);
            return new ResponseEntity<>(product, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{productId}/delete")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long productId) {
        productService.deleteProduct(productId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/{productId}/update")
    public ResponseEntity<Product> updateProduct(@PathVariable Long productId, @RequestBody Product product) {
        try {
            Product updatedProduct = productService.updateProduct(productId, product);
            return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{productId}")
    public ResponseEntity<Product> getProductById(@PathVariable Long productId, @RequestHeader("Authorization") String jwt) {
        try {
            Product product = productService.findProductById(productId);
            return new ResponseEntity<>(product, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<Product>> searchProducts(@RequestParam String query) {
        List<Product> products = productService.searchProducts(query);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Page<Product>> getAllProducts(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String brand,
            @RequestParam(required = false) String colors,
            @RequestParam(required = false) String sizes,
            @RequestParam(required = false) Integer minPrice,
            @RequestParam(required = false) Integer maxPrice,
            @RequestParam(required = false) Integer minDiscount,
            @RequestParam(required = false) String sort,
            @RequestParam(required = false) String stock,
            @RequestParam(required = false) Integer pageNumber,
            @RequestParam(required = false) String query
    ) {
        Page<Product> products = productService.getAllProducts(
                category, brand, colors, sizes, minPrice, maxPrice, minDiscount, sort, stock, pageNumber, query
        );
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/seller/{sellerId}")
    public ResponseEntity<List<Product>> getProductsBySellerId(@PathVariable Long sellerId) {
        List<Product> products = productService.getProductsBySellerId(sellerId);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }
}
