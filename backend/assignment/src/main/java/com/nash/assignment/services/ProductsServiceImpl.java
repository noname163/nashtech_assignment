package com.nash.assignment.services;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nash.assignment.constant.StatusEnum;
import com.nash.assignment.dto.response.ProductDtoRes;
import com.nash.assignment.modal.Category;
import com.nash.assignment.modal.Product;
import com.nash.assignment.repositories.CategoriesRepositories;
import com.nash.assignment.repositories.ProductsRepositories;
import com.nash.assignment.services.interfaces.ProductsService;

@Service
public class ProductsServiceImpl implements ProductsService {

    @Autowired
    ProductsRepositories productsRepositories;
    @Autowired
    CategoriesRepositories categoriesRepositories;
    @Autowired
    ModelMapper modelMapper;

    @Override
    public ProductDtoRes insertProduct(ProductDtoRes productDto, String categoriesValue) {
        if (productsRepositories.findByName(productDto.getName()) != null) {
            throw new RuntimeException("Product Name Exists.");
        }
        Category categories = categoriesRepositories.findByName(categoriesValue);
        if (categories == null) {
            throw new RuntimeException("Cannot Find Status Name: " + categoriesValue);
        }
        productDto.setStatus(StatusEnum.ACTIVE);
        productDto.setCategories(categories);
        Product product = modelMapper.map(productDto, Product.class);
        product = productsRepositories.save(product);
        return modelMapper.map(product, ProductDtoRes.class);
    }

    @Override
    public List<ProductDtoRes> getAllProducts() {
        List<ProductDtoRes> list = productsRepositories.findAll().stream()
                .map(product -> modelMapper.map(product, ProductDtoRes.class)).collect(Collectors.toList());
        return list;
    }

    @Override
    public ProductDtoRes updateProductStatus(ProductDtoRes productValue) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ProductDtoRes updateProductInformation(ProductDtoRes productValue) {
        // TODO Auto-generated method stub
        return null;
    }

}
