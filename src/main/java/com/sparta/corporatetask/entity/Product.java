package com.sparta.corporatetask.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long reviewCount = 0L; // 리뷰 개수
    private Double score = 0.0; // 평균 점수
}
