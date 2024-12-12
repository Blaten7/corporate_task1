package com.sparta.corporatetask.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "Review")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 리뷰 ID

    @Column(nullable = false)
    private Long productId; // 상품 ID

    @Column(nullable = false)
    private Long userId; // 유저 ID

    @Column(nullable = false)
    private int score; // 리뷰 점수 (1~5)

    @Column(nullable = false)
    private String content; // 리뷰 내용

    private String imageUrl; // 리뷰에 첨부된 이미지 URL

    @Column(name = "createdAt", updatable = false)
    private java.time.LocalDateTime createdAt; // 리뷰 작성 시간

    @PrePersist
    protected void onCreate() {
        createdAt = java.time.LocalDateTime.now(); // 리뷰 생성 시 현재 시간 설정
    }
}
