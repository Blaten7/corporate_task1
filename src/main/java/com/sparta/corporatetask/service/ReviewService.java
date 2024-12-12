package com.sparta.corporatetask.service;

import com.sparta.corporatetask.dto.ReviewDto;
import com.sparta.corporatetask.entity.Product;
import com.sparta.corporatetask.entity.Review;
import com.sparta.corporatetask.repository.ProductRepository;
import com.sparta.corporatetask.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Optional;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private ProductRepository productRepository;

    @Transactional
    public void createReview(Long productId, ReviewDto reviewDto, MultipartFile image) {
        Optional<Product> productOptional = productRepository.findById(productId);
        if (productOptional.isEmpty()) {
            throw new RuntimeException("Product not found");
        }

        // 유저가 이미 리뷰를 작성했는지 확인
        if (reviewRepository.existsByProductIdAndUserId(productId, reviewDto.getUserId())) {
            reviewRepository.deleteByProductIdAndUserId(productId, reviewDto.getUserId());
        }

        Review review = new Review();
        review.setProductId(productId);
        review.setImageUrl(image.getOriginalFilename());
        review.setUserId(reviewDto.getUserId());
        review.setScore(reviewDto.getScore());
        review.setContent(reviewDto.getContent());

        reviewRepository.save(review);

        updateProductReviewStats(productId);
    }

    private void updateProductReviewStats(Long productId) {
        double averageScore = reviewRepository.findByProductIdOrderByCreatedAtDesc(productId)
                .stream()
                .mapToInt(Review::getScore)
                .average()
                .orElse(0.0);
        DecimalFormat df = new DecimalFormat("#.##");
        double formattedScore = Double.parseDouble(df.format(averageScore));
        long reviewCount = reviewRepository.countByProductId(productId);

        Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found"));
        product.setScore(formattedScore);
        product.setReviewCount(reviewCount);
        productRepository.save(product);
    }
}
