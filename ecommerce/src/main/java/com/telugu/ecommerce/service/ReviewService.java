package com.telugu.ecommerce.service;

import com.telugu.ecommerce.model.Product;
import com.telugu.ecommerce.model.Review;
import com.telugu.ecommerce.model.User;
import com.telugu.ecommerce.request.CreateReviewRequest;

import java.util.List;

public interface ReviewService {
    Review createReview(CreateReviewRequest req, User user, Product product);
    List<Review> getReviewsByProductId(Long productId);
    Review updateReview(Long reviewId, String reviewText, double rating,Long userId);
    void deleteReview(Long reviewId,Long userId);
    Review getReviewById(Long reviewId);
}
