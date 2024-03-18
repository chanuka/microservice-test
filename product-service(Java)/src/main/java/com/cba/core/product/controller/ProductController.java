package com.cba.core.product.controller;

import com.cba.core.product.dto.ProductDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/devices")
public class ProductController {

    @GetMapping("/{productId}")
    public ResponseEntity<String> getProduct(@PathVariable Long productId) {
        // Business logic for fetching a product
        System.out.println("called me");
        return ResponseEntity.ok("Product details for ID: " + productId);
    }

    @PostMapping("/add")
    public ResponseEntity<ProductDto> getProduct(@RequestBody ProductDto productDto) {
        // Business logic for fetching a product
        productDto.setStatus("Success");

        return ResponseEntity.ok(productDto);
    }
}
