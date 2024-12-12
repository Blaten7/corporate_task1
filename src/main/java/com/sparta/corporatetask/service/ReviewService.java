package com.sparta.corporatetask.service;

import com.sparta.corporatetask.dto.ReviewDto;
import com.sparta.corporatetask.dto.ReviewResponseDto;
import com.sparta.corporatetask.entity.Product;
import com.sparta.corporatetask.entity.Review;
import com.sparta.corporatetask.repository.ProductRepository;
import com.sparta.corporatetask.repository.ReviewRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ReviewService {

    @Autowired
    private ReviewRepository rr;

    @Autowired
    private ProductRepository pr;

    @Transactional
    public void createReview(Long productId, ReviewDto reviewDto, MultipartFile image) {
        Optional<Product> productOptional = pr.findById(productId);
        if (productOptional.isEmpty()) {
            throw new RuntimeException("Product not found");
        }

        // 유저가 이미 리뷰를 작성했는지 확인
        if (rr.existsByProductIdAndUserId(productId, reviewDto.getUserId())) {
            rr.deleteByProductIdAndUserId(productId, reviewDto.getUserId());
        }

        Review review = new Review();
        review.setProductId(productId);
        review.setImageUrl(image.getOriginalFilename());
        review.setUserId(reviewDto.getUserId());
        review.setScore(reviewDto.getScore());
        review.setContent(reviewDto.getContent());

        rr.save(review);

        updateProductReviewStats(productId);
    }

    private void updateProductReviewStats(Long productId) {
        double averageScore = rr.findByProductIdOrderByCreatedAtDesc(productId)
                .stream()
                .mapToInt(Review::getScore)
                .average()
                .orElse(0.0);
        DecimalFormat df = new DecimalFormat("#.##");
        double formattedScore = Double.parseDouble(df.format(averageScore));
        long reviewCount = rr.countByProductId(productId);

        Product product = pr.findById(productId).orElseThrow(() -> new RuntimeException("Product not found"));
        product.setScore(formattedScore);
        product.setReviewCount(reviewCount);
        pr.save(product);
    }

//    public List<Review> getReviews(Long productId, Long cursor, int size) {
//        Pageable pageable = PageRequest.of(0, size);
//        if (cursor != null) {}
//        return reviewRepository.findByProductIdOrderByCreatedAtDesc(productId);
//    }


    public ReviewResponseDto getReviews(Long productId, Long cursor, int size) {
        long totalCount = rr.countByProductId(productId);

        double averageScore = rr.findByProductId(productId)
                .stream()
                .mapToInt(Review::getScore)
                .average()
                .orElse(0.0);

        List<Review> reviews;
        Pageable pageable = PageRequest.of(0, size);
        if (cursor == null) {
            // 최초 요청: 최신 데이터 가져오기
            reviews = rr.findByProductIdOrderByCreatedAtDesc(productId, pageable);
        } else {
            // 이후 요청: 커서 기반 페이징
            reviews = rr.findByProductIdAndIdLessThanOrderByCreatedAtDesc(productId, cursor, pageable);
        }
        log.info("================");
        log.info(reviews.toString());

        List<ReviewDto> reviewDtos = reviews.stream()
                .map(review -> new ReviewDto(
                        review.getId(),
                        review.getUserId(),
                        review.getScore(),
                        review.getContent(),
                        review.getImageUrl(),
                        review.getCreatedAt()
                ))
                .collect(Collectors.toList());

        Long nextCursor = reviews.isEmpty() ? null : reviews.get(reviews.size() - 1).getId();

        return new ReviewResponseDto(totalCount, averageScore, nextCursor, reviewDtos);
    }

}
