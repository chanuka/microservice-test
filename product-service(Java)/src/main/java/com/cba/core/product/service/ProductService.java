package com.cba.core.product.service;

import com.cba.core.product.dto.ProductDto;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    @Cacheable("products")
    public List<ProductDto> findAll() throws Exception {

        List<ProductDto> list = new ArrayList<>();

        list.add(ProductDto.builder().id("1").mobile("0775421588").name("Television").status("Available").build());
        list.add(ProductDto.builder().id("2").mobile("0776222154").name("Mobile").status("Available").build());

        System.out.println("list method called instead cache");

        list.add(ProductDto.builder().id("3").mobile("0715429682").name("Desk").status("Available").build());
        list.add(ProductDto.builder().id("4").mobile("0723548851").name("Carpet").status("Not Available").build());

        return list;
    }
}
