package com.sparta.corporatetask.controller;

import com.sparta.corporatetask.entity.Product;
import com.sparta.corporatetask.service.ProductsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Slf4j
@RestController
public class ProductsController {

    @Autowired
    private ProductsService productService;

    @PostMapping("/products/register")
    public void createProduct(@RequestBody Product product) {
        log.info("------------------product:"+product);
        productService.createProduct(product);
    }

    @GetMapping("/products/viewAll")
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }
}
