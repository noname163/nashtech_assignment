package com.nash.assignment.services;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nash.assignment.constant.ProductStatus;
import com.nash.assignment.dto.ProductDtoForAdmin;
import com.nash.assignment.dto.response.ProductDtoForUser;
import com.nash.assignment.exceptions.ObjectExistException;
import com.nash.assignment.exceptions.ObjectNotFoundException;
import com.nash.assignment.mapper.ImageMapper;
import com.nash.assignment.mapper.ProductMapper;
import com.nash.assignment.mapper.ProductMapperForAdmin;
import com.nash.assignment.modal.Category;
import com.nash.assignment.modal.Product;
import com.nash.assignment.repositories.CategoriesRepositories;
import com.nash.assignment.repositories.ProductsRepositories;
import com.nash.assignment.services.interfaces.ProductsService;

@Service
public class ProductsServiceImpl implements ProductsService {

    ProductsRepositories productsRepositories;

    CategoriesRepositories categoriesRepositories;

    ProductMapper productMapper;

    ImageMapper imageMapper;

    ImageServiceImpl imageServiceImpl;

    ProductMapperForAdmin productMapperForAdmin;

    @Autowired
    public ProductsServiceImpl(ProductsRepositories productsRepositories, CategoriesRepositories categoriesRepositories,
            ProductMapper productMapper, ImageMapper imageMapper, ProductMapperForAdmin productMapperForAdmin, ImageServiceImpl imageServiceImpl) {
        this.productsRepositories = productsRepositories;
        this.categoriesRepositories = categoriesRepositories;
        this.productMapper = productMapper;
        this.imageMapper = imageMapper;
        this.productMapperForAdmin = productMapperForAdmin;
        this.imageServiceImpl = imageServiceImpl;
    }


    @Override
    public ProductDtoForAdmin insertProduct(ProductDtoForAdmin productDto) {
        if (productsRepositories.findByName(productDto.getName()) != null) {
            throw new ObjectExistException("Product Name Exists.");
        }
        Category categories = categoriesRepositories.findByName(productDto.getCategories());
        if (categories == null) {
            throw new ObjectNotFoundException("Cannot Find Category Name: " + categories);
        }
        if(productDto.getImages() == null||
         productDto.getImages().isEmpty()){
            throw new ObjectNotFoundException("Image Null");
        }
        productDto.setStatus(ProductStatus.AVAILABLE);
        Product product = productMapperForAdmin.mapDtoToEntity(productDto);
        LocalDate date = LocalDate.now();
        product.setCreatedDate(date.toString());
        product = productsRepositories.save(product);
        imageServiceImpl.insertMultipeImages(productDto.getImages(), productDto);
        return productMapperForAdmin.mapEntityToDto(product);
    }

    @Override
    public List<ProductDtoForUser> getAllProductsAvailble() {
        return productsRepositories.findByStatus(ProductStatus.AVAILABLE).stream()
                .map(product -> productMapper.mapEntityToDto(product)).collect(Collectors.toList());
    }

    @Override
    public ProductDtoForAdmin updateProductStatus(long id, ProductStatus statusValue) {
        Optional<Product> productOtp = productsRepositories.findById(id);
        if (productOtp.isEmpty()) {
            throw new ObjectNotFoundException("Cannot Find Product With Id: " + id);
        }
        Product productDataBase = productOtp.get();
        LocalDate date = LocalDate.now();
        productDataBase.setStatus(statusValue);
        productDataBase.setUpdateDate(date.toString());
        Product product = productsRepositories.save(productDataBase);

        return productMapperForAdmin.mapEntityToDto(product);
    }

    @Override
    public ProductDtoForAdmin updateProductInformation(ProductDtoForAdmin productValue) {
        Optional<Product> productOtp = productsRepositories.findById(productValue.getId());
        if (productOtp.isEmpty()) {
            throw new ObjectNotFoundException("Cannot Find Product With Id: " + productValue.getId());
        }
        Product productDatabase = productOtp.get();
        
        Category category = categoriesRepositories.findByName(productValue.getCategories());
        if (category == null) {
            throw new ObjectNotFoundException("Cannot Found Category Name: " + productValue.getCategories());
        }
        LocalDate date = LocalDate.now();
        productDatabase.setUpdateDate(date.toString());
        productDatabase.setName(productValue.getName());
        productDatabase.setPrice(productValue.getPrice());
        productDatabase.setDescription(productValue.getDescription());
        productDatabase.setCategories(category);
        productDatabase.setImages(imageMapper.mapImageProductDtoToEntity(productValue.getImages(),productDatabase));
        productsRepositories.save(productDatabase);
        return productMapperForAdmin.mapEntityToDto(productDatabase);
    }

    public List<ProductDtoForUser> getProductByCategories(String categoriesName) {
        Category category = categoriesRepositories.findByName(categoriesName);
        if (category == null) {
            throw new ObjectNotFoundException("Cannot Find Category Name: " + categoriesName);
        }
        List<Product> products = productsRepositories.findByCategories(category);
        return productMapper.mapEntityToDto(products);
    }

    public List<ProductDtoForAdmin> getAllProductsAdmin() {
        return productsRepositories.findAll().stream()
                .map(product -> 
                productMapperForAdmin.mapEntityToDto(product))
                .collect(Collectors.toList());
    }

    public List<ProductDtoForUser> findProductByName(String name){
        List<Product> productList = productsRepositories.findByNameContainingIgnoreCaseAndStatus(name, ProductStatus.AVAILABLE);
        if(productList == null || productList.isEmpty()){
            return Collections.emptyList();
        }
        return productMapper.mapEntityToDto(productList);
        
    }

    

}
