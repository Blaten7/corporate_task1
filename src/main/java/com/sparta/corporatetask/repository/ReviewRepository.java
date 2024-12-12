package com.sparta.corporatetask.repository;

import com.sparta.corporatetask.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findByProductIdOrderByCreatedAtDesc(Long productId);
    boolean existsByProductIdAndUserId(Long productId, Long userId);
    void deleteByProductIdAndUserId(Long userId, Long productId);
    long countByProductId(Long productId);

}
