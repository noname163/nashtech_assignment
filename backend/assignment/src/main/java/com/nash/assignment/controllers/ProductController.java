package com.nash.assignment.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nash.assignment.dto.response.ProductDtoForUser;
import com.nash.assignment.modal.Category;
import com.nash.assignment.services.CategoriesServiceImpl;
import com.nash.assignment.services.PaginationServiceImpl;
import com.nash.assignment.services.ProductsServiceImpl;

@RestController
@RequestMapping("/products")
public class ProductController {
    ProductsServiceImpl productsServiceImpl;
    CategoriesServiceImpl categoriesServiceImpl;
    PaginationServiceImpl paginationServiceImpl;

    @Autowired
    public ProductController(ProductsServiceImpl productsServiceImpl, CategoriesServiceImpl categoriesServiceImpl, PaginationServiceImpl paginationServiceImpl) {
        this.productsServiceImpl = productsServiceImpl;
        this.categoriesServiceImpl = categoriesServiceImpl;
        this.paginationServiceImpl = paginationServiceImpl;
    }
    
    @GetMapping()
    public ResponseEntity<List<ProductDtoForUser>> displayProduct(){
        List<ProductDtoForUser> productDto = productsServiceImpl.getAllProductsAvailble();
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
    @GetMapping(value="/product-pagination/{page}")
    public ResponseEntity<List<ProductDtoForUser>> getAllUsePagination(@PathVariable int page){
        List<ProductDtoForUser> productList = paginationServiceImpl.getAllProductPagination(page,6);
        return ResponseEntity.status(HttpStatus.OK).body(
            productList
        );
    }
    @PostMapping(value="/find-by-name/{name}")
    public ResponseEntity<List<ProductDtoForUser>> findByProductName(@PathVariable String name){
        List<ProductDtoForUser> productList = productsServiceImpl.findProductByName(name);
        return ResponseEntity.status(HttpStatus.OK).body(
            productList
        );
    }
    @PostMapping(value = "/by-categories/{category}")
    public ResponseEntity<List<ProductDtoForUser>> displayProductByCategories(@PathVariable String category){
        List<ProductDtoForUser> productDto = productsServiceImpl.getProductByCategories(category);
        return ResponseEntity.status(HttpStatus.OK).body(
            productDto
        );
    }

    

    
}
