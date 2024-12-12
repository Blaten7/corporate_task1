package com.sparta.corporatetask.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class ReviewResponseDto {
    private long totalCount;  // 총 리뷰 수
    private double score;     // 평균 점수
    private Long cursor;      // 마지막 리뷰 ID
    private List<ReviewDto> reviews; // 리뷰 목록
}
