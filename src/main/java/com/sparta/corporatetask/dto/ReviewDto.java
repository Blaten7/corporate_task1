package com.sparta.corporatetask.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDto {
    private Long userId;
    private int score; // 1~5 점수
    private String content; // 리뷰 내용
    private String imageUrl; // 이미지 주소
}
