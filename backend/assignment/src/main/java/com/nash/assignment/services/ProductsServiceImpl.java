package com.nash.assignment.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nash.assignment.constant.StatusEnum;
import com.nash.assignment.dto.ProductDto;
import com.nash.assignment.exceptions.InformationNotValidException;
import com.nash.assignment.exceptions.ObjectExistException;
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
            ProductMapper productMapper) {
        this.productsRepositories = productsRepositories;
        this.categoriesRepositories = categoriesRepositories;
        this.productMapper = productMapper;
    }

    public Product insertProduct1(Product product) {
        return productsRepositories.save(product);
    }

    @Override
    public ProductDto insertProduct(ProductDto productDto) {
        if (productsRepositories.findByName(productDto.getName()) != null) {
            throw new ObjectExistException("Product Name Exists.");
        }
        Category categories = categoriesRepositories.findByName(productDto.getCategories());
        if (categories == null) {
            throw new ObjectNotFoundException("Cannot Find Category Name: " + categories);
        }
        productDto.setStatus(StatusEnum.ACTIVE);
        Product product = productMapper.mapDtoToEntity(productDto);
        product = productsRepositories.save(product);
        return productMapper.mapEntityToDto(product);
    }

    @Override
    public List<ProductDto> getAllProducts() {
        return productsRepositories.findAll().stream()
                .map(product -> productMapper.mapEntityToDto(product)).collect(Collectors.toList());
    }

    @Override
    public ProductDto updateProductStatus(long id, int statusVale) {
        Optional<Product> productotp = productsRepositories.findById(id);
        if (productotp.isEmpty()) {
            throw new ObjectNotFoundException("Cannot Find Product With Id: " + id);
        }
        if (statusVale < 1 || statusVale > 2) {
            throw new InformationNotValidException("Status Not Valid.");
        }
        Product productDataBase = productotp.get();
        StatusEnum statusEnum = statusVale == 1 ? StatusEnum.ACTIVE : StatusEnum.DEACTIVE;
        productDataBase.setStatus(statusEnum);
        Product product = productsRepositories.save(productDataBase);

        return productMapper.mapEntityToDto(product);
    }

    @Override
    public ProductDto updateProductInformation(ProductDto productValue) {
        Optional<Product> productOtp = productsRepositories.findById(productValue.getId());
        Category category = categoriesRepositories.findByName(productValue.getCategories());
        if (productOtp.isEmpty()) {
            throw new ObjectNotFoundException("Cannot Find Product With Id: " + productValue.getId());
        }
        if (category == null) {
            throw new ObjectNotFoundException("Cannot Found Category Name: " + productValue.getCategories());
        }
        Product productDatabase = productOtp.get();
        productDatabase.setName(productValue.getName());
        productDatabase.setPrice(productValue.getPrice());
        productDatabase.setCategories(category);
        productDatabase.setImages(productValue.getImages());
        return productMapper.mapEntityToDto(productDatabase);
    }

}
