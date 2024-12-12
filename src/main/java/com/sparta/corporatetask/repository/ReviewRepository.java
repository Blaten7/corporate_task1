package com.sparta.corporatetask.repository;

import com.sparta.corporatetask.entity.Review;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findByProductIdOrderByCreatedAtDesc(Long productId);
    boolean existsByProductIdAndUserId(Long productId, Long userId);
    void deleteByProductIdAndUserId(Long userId, Long productId);
    long countByProductId(Long productId);

    @Query("SELECT r FROM Review r WHERE r.productId = :productId AND r.id < :cursor ORDER BY r.createdAt DESC")
    List<Review> findByProductIdAndIdLessThanOrderByCreatedAtDesc(
            @Param("productId") Long productId,
            @Param("cursor") Long cursor,
            Pageable  pageable
    );


    @Query("SELECT r FROM Review r WHERE r.productId = :productId ORDER BY r.createdAt DESC")
    List<Review> findByProductIdOrderByCreatedAtDesc(@Param("productId") Long productId, Pageable pageable);


    List<Review> findByProductId(Long productId);
}
