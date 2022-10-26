package com.nash.assignment.controllers.admin;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.nash.assignment.dto.ProductDtoForAdmin;
import com.nash.assignment.mapper.ImageMapper;
import com.nash.assignment.modal.Image;
import com.nash.assignment.services.CloudinaryServiceImpl;
import com.nash.assignment.services.FileServiceImpl;
import com.nash.assignment.services.ImageServiceImpl;
import com.nash.assignment.services.ProductsServiceImpl;

@RestController
@RequestMapping("/admin/product")
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
public class ProductController {
        @Autowired
        ProductsServiceImpl productsServiceImpl;
        @Autowired
        FileServiceImpl fileServiceImpl;
        @Autowired
        ImageServiceImpl imageServiceImpl;
        @Autowired
        ImageMapper imageMapper;
        @Autowired CloudinaryServiceImpl cloudinaryServiceImpl;

        @PostMapping()
        public ResponseEntity<ProductDtoForAdmin> insertProduct(@Valid @RequestBody ProductDtoForAdmin productDto,
                        MultipartFile[] productimages)
                        throws IOException {
                List<String> urls =  cloudinaryServiceImpl.uploadImages(productimages);
                // ResponseEntity<List<String>> saveImage = fileServiceImpl.saveMultipleFile(
                //                 "product/" + productDto.getName(),
                //                 productimages);
                productsServiceImpl.insertProduct(productDto);
                // List<String> urls = saveImage.getBody();
                List<Image> images = imageServiceImpl.insertMultipeImages(urls, productDto);
                productDto.setImages(imageMapper.mapEntityToImageProductDto(images));
                ProductDtoForAdmin product = productsServiceImpl.updateProductInformation(productDto);
                return ResponseEntity.status(HttpStatus.CREATED).body(
                                product);
        }

        @PatchMapping(value = "/active/{id}")
        public ResponseEntity<ProductDtoForAdmin> activeProduct(@PathVariable long id) {
                final int status = 1;
                ProductDtoForAdmin productDto = productsServiceImpl.updateProductStatus(id, status);
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
                final int status = 2;
                ProductDtoForAdmin productDto = productsServiceImpl.updateProductStatus(id, status);
                return ResponseEntity.status(HttpStatus.OK).body(
                                productDto);
        }

}
