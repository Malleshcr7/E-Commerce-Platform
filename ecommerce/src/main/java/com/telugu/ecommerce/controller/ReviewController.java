package com.telugu.ecommerce.controller;

import com.telugu.ecommerce.model.Product;
import com.telugu.ecommerce.model.Review;
import com.telugu.ecommerce.model.User;
import com.telugu.ecommerce.request.CreateReviewRequest;
import com.telugu.ecommerce.response.ApiResponse;
import com.telugu.ecommerce.service.ProductService;
import com.telugu.ecommerce.service.ReviewService;
import com.telugu.ecommerce.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;
    private final UserService userService;
    private final ProductService productService;

    @PostMapping
    public ResponseEntity<Review> createReview(@RequestBody CreateReviewRequest req,
                                               @PathVariable Long productId,
                                               @RequestHeader("Authorization") String jwt) {
        User user = userService.findUserByJwtToken(jwt);
        Product product = productService.findProductById(productId);
        Review createdReview = reviewService.createReview(req, user, product);
        return ResponseEntity.ok(createdReview);
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<Review>> getReviewsByProductId(@PathVariable Long productId) {
        List<Review> reviews = reviewService.getReviewsByProductId(productId);
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }

    @PutMapping("/reviews/{reviewId}")
    public ResponseEntity<Review> updateReview(@PathVariable Long reviewId,
                                               @RequestBody CreateReviewRequest req,
                                               @RequestHeader("Authorization") String jwt) {
        User user = userService.findUserByJwtToken(jwt);
        Review updatedReview = reviewService.updateReview(reviewId, req.getReviewText(), req.getRating(), user.getId());
        return ResponseEntity.ok(updatedReview);
    }

    @DeleteMapping("/reviews/{reviewId}")
    public ResponseEntity<ApiResponse> deleteReview(@PathVariable Long reviewId,
                                                    @RequestHeader("Authorization") String jwt) {
        User user = userService.findUserByJwtToken(jwt);
        ApiResponse res=new ApiResponse();
        res.setMessage("Review Deleted");

        return ResponseEntity.ok(res);
    }

    @GetMapping("/{reviewId}")
    public ResponseEntity<Review> getReviewById(@PathVariable Long reviewId) {
        Review review = reviewService.getReviewById(reviewId);
        return new ResponseEntity<>(review, HttpStatus.OK);
    }
}
