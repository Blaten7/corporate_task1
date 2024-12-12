package com.sparta.corporatetask.repository;

import com.sparta.corporatetask.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findById(Long id); // 상품 ID로 상품 조회

}
