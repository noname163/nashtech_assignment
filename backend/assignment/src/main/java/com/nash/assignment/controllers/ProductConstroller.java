package com.nash.assignment.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nash.assignment.dto.request.CategoriesDto;
import com.nash.assignment.dto.response.ProductDtoForUser;
import com.nash.assignment.modal.Category;
import com.nash.assignment.services.CategoriesServiceImpl;
import com.nash.assignment.services.ProductsServiceImpl;

@RestController
@RequestMapping("/products")
public class ProductConstroller {
    ProductsServiceImpl productsServiceImpl;
    CategoriesServiceImpl categoriesServiceImpl;

    @Autowired
    public ProductConstroller(ProductsServiceImpl productsServiceImpl, CategoriesServiceImpl categoriesServiceImpl) {
        this.productsServiceImpl = productsServiceImpl;
        this.categoriesServiceImpl = categoriesServiceImpl;
    }
    
    @GetMapping()
    public ResponseEntity<List<ProductDtoForUser>> displayProduct(){
        List<ProductDtoForUser> productDto = productsServiceImpl.getAllProducts();
        return ResponseEntity.status(HttpStatus.OK).body(
            productDto
        );
    }
    @PostMapping(value = "/by-categories/{category}")
    public ResponseEntity<List<ProductDtoForUser>> displayProductByCategories(@PathVariable String category){
        List<ProductDtoForUser> productDto = productsServiceImpl.getProductByCategories(category);
        return ResponseEntity.status(HttpStatus.OK).body(
            productDto
        );
    }

    @GetMapping(value = "/categories")
    public ResponseEntity<List<Category>> getAllCategories(){
        return ResponseEntity.status(HttpStatus.OK).body(
            categoriesServiceImpl.getAllCategories()
        );
    }
}
