package com.sparta.corporatetask.controller;

import com.sparta.corporatetask.dto.ReviewDto;
import com.sparta.corporatetask.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/products")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @PostMapping("/{productId}/reviews")
    public ResponseEntity<Void> createReview(@PathVariable Long productId, @RequestPart("file") MultipartFile file,
                                             @RequestPart("review") ReviewDto reviewDto) {
        reviewService.createReview(productId, reviewDto);
        // 파일 업로드 로직은 더미로 구현
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{productId}/reviews")
    public ResponseEntity<?> getReviews(@PathVariable Long productId,
                                        @RequestParam(defaultValue = "10") int size,
                                        @RequestParam(required = false) Long cursor) {
        // 리뷰 조회 로직을 서비스에서 구현
        // 예시로 응답을 반환
        return ResponseEntity.ok().body(reviewService.getReviews(productId, cursor, size));
    }
}
