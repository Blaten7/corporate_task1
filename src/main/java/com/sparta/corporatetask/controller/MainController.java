package com.sparta.corporatetask.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/products")
    public String products() {
        return "products";
    }
}
