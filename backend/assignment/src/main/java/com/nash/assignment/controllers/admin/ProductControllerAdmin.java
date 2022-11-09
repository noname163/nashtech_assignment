package com.nash.assignment.controllers.admin;

import java.io.IOException;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nash.assignment.constant.ProductStatus;
import com.nash.assignment.dto.ProductDtoForAdmin;
import com.nash.assignment.mapper.ImageMapper;
import com.nash.assignment.services.CloudinaryServiceImpl;
import com.nash.assignment.services.FileServiceImpl;
import com.nash.assignment.services.ImageServiceImpl;
import com.nash.assignment.services.ProductsServiceImpl;

@RestController
@RequestMapping("/admin/product")
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
public class ProductControllerAdmin {

    @Autowired
    ProductsServiceImpl productsServiceImpl;
    @Autowired
    FileServiceImpl fileServiceImpl;
    @Autowired
    ImageServiceImpl imageServiceImpl;
    @Autowired
    ImageMapper imageMapper;
    @Autowired
    CloudinaryServiceImpl cloudinaryServiceImpl;

    @GetMapping()
    public ResponseEntity<List<ProductDtoForAdmin>> getAllProduct(){
        return ResponseEntity.status(HttpStatus.OK).body(
            productsServiceImpl.getAllProductsAdmin()
        );
    }

    @PostMapping()
    public ResponseEntity<ProductDtoForAdmin> insertProduct(@Valid @RequestBody ProductDtoForAdmin productDto)
            throws IOException {
                ProductDtoForAdmin productDtoForAdmin = productsServiceImpl.insertProduct(productDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(
         productDtoForAdmin   );
    }

    @PatchMapping(value = "/active/{id}")
    public ResponseEntity<ProductDtoForAdmin> activeProduct(@PathVariable long id) {
        ProductDtoForAdmin productDto = productsServiceImpl.updateProductStatus(id, ProductStatus.AVAILABLE);
        return ResponseEntity.status(HttpStatus.OK).body(
                productDto);
    }

    @PutMapping(value = "/update")
    public ResponseEntity<ProductDtoForAdmin> updateProduct(@RequestBody ProductDtoForAdmin productDto) {
        ProductDtoForAdmin product = productsServiceImpl.updateProductInformation(productDto);
        return ResponseEntity.status(HttpStatus.OK).body(
                product);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<ProductDtoForAdmin> deleteProduct(@PathVariable long id) {
        ProductDtoForAdmin productDto = productsServiceImpl.updateProductStatus(id, ProductStatus.UNAVAILABLE);
        return ResponseEntity.status(HttpStatus.OK).body(
                productDto);
    }

}
