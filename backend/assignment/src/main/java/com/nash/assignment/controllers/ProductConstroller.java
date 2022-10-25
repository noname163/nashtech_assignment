package com.nash.assignment.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nash.assignment.dto.ProductDto;
import com.nash.assignment.services.ProductsServiceImpl;

@RestController
@RequestMapping("/products")
public class ProductConstroller {
    @Autowired ProductsServiceImpl productsServiceImpl;
    @GetMapping()
    public ResponseEntity<List<ProductDto>> displayProduct(){
        List<ProductDto> productDto = productsServiceImpl.getAllProducts();
        return ResponseEntity.status(HttpStatus.OK).body(
            productDto
        );
    }
}
