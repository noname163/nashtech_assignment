package com.nash.assignment.controllers.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.validation.Valid;

import com.nash.assignment.dto.ProductDto;
import com.nash.assignment.services.ProductsServiceImpl;

@Controller
@RequestMapping("/admin/product")
public class ProductController {
    @Autowired ProductsServiceImpl productsServiceImpl;
    @PostMapping(value = "/insert")
    public ResponseEntity<ProductDto> insertProduct(@Valid @RequestBody ProductDto productDto){
        ProductDto product = productsServiceImpl.insertProduct(productDto);
        return ResponseEntity.status(HttpStatus.OK).body(
            product
        );
    }
}
