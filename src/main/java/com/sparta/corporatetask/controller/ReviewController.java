package com.sparta.corporatetask.controller;

import com.sparta.corporatetask.dto.ReviewDto;
import com.sparta.corporatetask.entity.Review;
import com.sparta.corporatetask.service.ReviewService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/products")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @PostMapping(value = "/{productId}/reviews", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_OCTET_STREAM_VALUE})
    public void createReview(@PathVariable Long productId,@RequestPart("review") @Valid ReviewDto reviewDto,
                             @RequestPart(value = "image", required = false) MultipartFile file) {
        reviewService.createReview(productId, reviewDto, file);
    }

    @GetMapping("/{productId}/reviews")
    public ResponseEntity<?> getReviews(@PathVariable Long productId,
                                        @RequestParam(defaultValue = "10") int size,
                                        @RequestParam(required = false) Long cursor) {
        return ResponseEntity.ok().body(reviewService.getReviews(productId, cursor, size));
    }

}
