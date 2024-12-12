package com.sparta.corporatetask.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDto {
    private long id;
    private Long userId;
    private int score; // 1~5 점수
    private String content; // 리뷰 내용
    private String imageUrl; // 이미지 주소
    private LocalDateTime createdAt;


//    public ReviewDto(Long id, Long userId, int score, String content, String imageUrl, LocalDateTime createdAt) {
//        this.userId = userId;
//        this.score = score;
//        this.content = content;
//        this.imageUrl = imageUrl;
//    }
}
