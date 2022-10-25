package com.nash.assignment.controllers.admin;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.nash.assignment.dto.ProductDto;
import com.nash.assignment.mapper.ImageMapper;
import com.nash.assignment.modal.Image;
import com.nash.assignment.services.FileServiceImpl;
import com.nash.assignment.services.ImageServiceImpl;
import com.nash.assignment.services.ProductsServiceImpl;

@RestController
@RequestMapping("/admin/product")
public class ProductController {
    @Autowired
    ProductsServiceImpl productsServiceImpl;
    @Autowired
    FileServiceImpl fileServiceImpl;
    @Autowired
    ImageServiceImpl imageServiceImpl;
    @Autowired
    ImageMapper imageMapper;

    @PostMapping()
    public ResponseEntity<ProductDto> insertProduct(@Valid ProductDto productDto, MultipartFile[] productimages)
            throws IOException {

        ResponseEntity<List<String>> saveImage = fileServiceImpl.saveMultipleFile("product/" + productDto.getName(),
                productimages);
        productsServiceImpl.insertProduct(productDto);
        List<String> urls = saveImage.getBody();
        Set<Image> images = imageServiceImpl.insertMultipeImages(urls, productDto);
        productDto.setImages(imageMapper.mapEntityToImageProductDto(images));
        ProductDto product = productsServiceImpl.updateProductInformation(productDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                product);
    }

    @PatchMapping(value = "/active/{id}")
    public ResponseEntity<ProductDto> activeProduct(@PathVariable long id) {
        final int status = 1;
        ProductDto productDto = productsServiceImpl.updateProductStatus(id, status);
        return ResponseEntity.status(HttpStatus.OK).body(
                productDto);
    }

    @PutMapping(value = "/update")
    public ResponseEntity<ProductDto> updateProduct(@RequestBody ProductDto productDto) {
        ProductDto product = productsServiceImpl.updateProductInformation(productDto);
        return ResponseEntity.status(HttpStatus.OK).body(
                product);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<ProductDto> deleteProduct(@PathVariable long id) {
        final int status = 2;
        ProductDto productDto = productsServiceImpl.updateProductStatus(id, status);
        return ResponseEntity.status(HttpStatus.OK).body(
                productDto);
    }

}
