package com.sparta.corporatetask.service;

import com.sparta.corporatetask.entity.Product;
import com.sparta.corporatetask.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductsService {

    @Autowired
    private ProductRepository pr;


    @Transactional
    public void createProduct(Product product) {
        pr.save(product);
    }

    public List<Product> getAllProducts() {
        return pr.findAll();
    }
}
