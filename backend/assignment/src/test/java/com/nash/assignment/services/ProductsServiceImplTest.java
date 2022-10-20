package com.nash.assignment.services;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import com.nash.assignment.dto.ProductDto;
import com.nash.assignment.modal.Category;
import com.nash.assignment.modal.Product;
import com.nash.assignment.repositories.CategoriesRepositories;
import com.nash.assignment.repositories.ProductsRepositories;

public class ProductsServiceImplTest {

    ProductsServiceImpl productsServiceImpl;

    CategoriesRepositories categoriesRepositories;

    ModelMapper modelMapper;

    ProductsRepositories productsRepositories;

    Category category;

    ProductDto productDtoResp;

    Product product;

    @BeforeEach
    void setUpObject() {
        modelMapper = mock(ModelMapper.class);
        category = mock(Category.class);
        productsRepositories = mock(ProductsRepositories.class);
        productDtoResp = mock(ProductDto.class);
        product = mock(Product.class);
        categoriesRepositories = mock(CategoriesRepositories.class);
    }

    @Test
    void testGetAllProducts() {

    }

    @Test
    void testInsertProduct_WhenDataValid_ShouldReturnProductDto() {
        ProductDto productDto1 = mock(ProductDto.class);
        when(categoriesRepositories.findByName(productDtoResp.getCategories())).thenReturn(category);
        when(productsServiceImpl.insertProduct(productDtoResp)).thenReturn(productDto1);
        ProductDto expected = productsServiceImpl.insertProduct(productDtoResp);
        Assertions.assertEquals(expected, productDto1);
    }

    @Test
    void testUpdateProductInformation_WhenDataValid_ShouldReturnProductResp() {
        
    }

    @Test
    void testUpdateProductStatus() {

    }
}
