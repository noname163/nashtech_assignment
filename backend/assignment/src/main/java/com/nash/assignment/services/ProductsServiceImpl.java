package com.nash.assignment.services;

import java.io.ObjectInputFilter.Status;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nash.assignment.constant.StatusEnum;
import com.nash.assignment.dto.ProductDto;
import com.nash.assignment.exceptions.InformationNotValidException;
import com.nash.assignment.exceptions.ObjectNotFoundException;
import com.nash.assignment.mapper.ProductMapper;
import com.nash.assignment.modal.Category;
import com.nash.assignment.modal.Product;
import com.nash.assignment.repositories.CategoriesRepositories;
import com.nash.assignment.repositories.ProductsRepositories;
import com.nash.assignment.services.interfaces.ProductsService;

@Service
public class ProductsServiceImpl implements ProductsService {

    ProductsRepositories productsRepositories;

    CategoriesRepositories categoriesRepositories;

    ModelMapper modelMapper;

    ProductMapper productMapper;

    

    @Autowired
    public ProductsServiceImpl(ProductsRepositories productsRepositories, CategoriesRepositories categoriesRepositories,
            ModelMapper modelMapper, ProductMapper productMapper) {
        this.productsRepositories = productsRepositories;
        this.categoriesRepositories = categoriesRepositories;
        this.modelMapper = modelMapper;
        this.productMapper = productMapper;
    }

    public Product insertProduct1(Product product){
        return productsRepositories.save(product);
    }

    @Override
    public ProductDto insertProduct(ProductDto productDto) {
        if (productsRepositories.findByName(productDto.getName()) != null) {
            throw new RuntimeException("Product Name Exists.");
        }
        Category categories = categoriesRepositories.findByName(productDto.getCategories());
        if (categories == null) {
            throw new RuntimeException("Cannot Find Category Name: " + categories);
        }
        productDto.setStatus(StatusEnum.ACTIVE);
        Product product = productMapper.mapDtoToEntity(productDto);
        // product.setCategories(categories);
        product = productsRepositories.save(product);
        return productMapper.mapEntityToDto(product) ;
    }

    @Override
    public List<ProductDto> getAllProducts() {
        return productsRepositories.findAll().stream()
                .map(product -> modelMapper.map(product, ProductDto.class)).collect(Collectors.toList());
    }

    @Override
    public ProductDto updateProductStatus(ProductDto productValue, int statusVale) {
        Product productDataBase = productsRepositories.findByName(productValue.getName());
        if (productDataBase == null) {
            throw new ObjectNotFoundException("Cannot Find Product Name: " + productValue.getName());
        }
        if (statusVale < 1 || statusVale > 2) {
            throw new InformationNotValidException("Status Not Valid.");
        }
        StatusEnum statusEnum = statusVale == 1 ? StatusEnum.ACTIVE : StatusEnum.DEACTIVE;
        productDataBase.setStatus(statusEnum);
        Product product = productsRepositories.save(productDataBase);

        return modelMapper.map(product, ProductDto.class);
    }

    @Override
    public ProductDto updateProductInformation(ProductDto productValue) {
        Product productDatabase = productsRepositories.findByName(productValue.getName());
        Category category = categoriesRepositories.findByName(productValue.getCategories());
        if(category == null){
            throw new ObjectNotFoundException("Cannot Found Category Name: " + productValue.getCategories());
        }
        productDatabase.setName(productValue.getName());
        productDatabase.setPrice(productValue.getPrice());
        productDatabase.setCategories(category);
        return modelMapper.map(productDatabase, ProductDto.class);
    }

}
