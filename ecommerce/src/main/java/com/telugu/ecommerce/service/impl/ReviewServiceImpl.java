package com.telugu.ecommerce.service.impl;

import com.telugu.ecommerce.model.Product;
import com.telugu.ecommerce.model.Review;
import com.telugu.ecommerce.model.User;
import com.telugu.ecommerce.repository.ProductRepository;
import com.telugu.ecommerce.repository.ReviewRepository;
import com.telugu.ecommerce.request.CreateReviewRequest;
import com.telugu.ecommerce.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final ProductRepository productRepository;

    @Override
    public Review createReview(CreateReviewRequest req, User user, Product product) {
        Review review = new Review();
        review.setReviewText(req.getReviewText());
        review.setRating(req.getRating());
        review.setProductImages(req.getProductImages());
        review.setUser(user);
        review.setProduct(product);

        product.getReviews().add(review);
        return reviewRepository.save(review);
    }

    @Override
    public List<Review> getReviewsByProductId(Long productId) {
        return reviewRepository.findByProductId(productId);
    }

    @Override
    public Review updateReview(Long reviewId, String reviewText, double rating, Long userId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("Review not found with id: " + reviewId));

        // Ensure the review belongs to the user
        if (!review.getUser().getId().equals(userId)) {
            throw new IllegalArgumentException("User not authorized to update this review");
        }

        review.setReviewText(reviewText);
        review.setRating(rating);

        return reviewRepository.save(review);
    }

    @Override
    public void deleteReview(Long reviewId, Long userId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new IllegalArgumentException("Review not found with id: " + reviewId));

        // Ensure the review belongs to the user
        if (!review.getUser().getId().equals(userId)) {
            throw new IllegalArgumentException("User not authorized to delete this review");
        }

        reviewRepository.delete(review);
    }

    @Override
    public Review getReviewById(Long reviewId) {
        return reviewRepository.findById(reviewId).orElseThrow(() -> new IllegalArgumentException("Review not found with id: " + reviewId));
    }
}
