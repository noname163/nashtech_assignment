package com.nash.assignment.services;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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

public class ProductsServiceImplTest {

    ProductsServiceImpl productsServiceImpl;

    CategoriesRepositories categoriesRepositories;

    ProductMapper productMapper;

    ProductsRepositories productsRepositories;

    Category category;

    ProductDto productDtoResp;

    Product product;

    @BeforeEach
    void setUpObject() {
        productMapper = mock(ProductMapper.class);
        category = mock(Category.class);
        productsRepositories = mock(ProductsRepositories.class);
        productDtoResp = mock(ProductDto.class);
        product = mock(Product.class);
        categoriesRepositories = mock(CategoriesRepositories.class);
        productsServiceImpl = new ProductsServiceImpl(productsRepositories, categoriesRepositories, productMapper);
    }

    @Test
    void GetAllProducts_ShouldReturnListProductDto() {
        List<ProductDto> expectedList = new ArrayList<>();
        List<Product> productList = new ArrayList<>();
        productList.add(product);
        when(productsRepositories.findAll()).thenReturn(productList);
        when(productMapper.mapEntityToDto(product)).thenReturn(productDtoResp);
        expectedList.add(productDtoResp);

        List<ProductDto> actual = productsServiceImpl.getAllProducts();
        assertThat(expectedList, is(actual));

    }

    @Test
    void InsertProduct_WhenDataValid_ShouldReturnProductDto() {
        ProductDto expected = mock(ProductDto.class);
        when(categoriesRepositories.findByName(productDtoResp.getCategories())).thenReturn(category);
        when(productMapper.mapDtoToEntity(expected)).thenReturn(product);
        when(productsRepositories.save(product)).thenReturn(product);
        when(productMapper.mapEntityToDto(product)).thenReturn(expected);
        ProductDto actual = productsServiceImpl.insertProduct(expected);
        verify(expected).setStatus(StatusEnum.ACTIVE);
        assertThat(expected, is(actual));
    }

    @Test
    void InsertProduct_WhenProductExist_ShouldThrowObjectNotFoundException() {
        when(productsRepositories.findByName(productDtoResp.getName())).thenReturn(product);
        ObjectExistException actual = Assertions.assertThrows(ObjectExistException.class, 
                () -> productsServiceImpl.insertProduct(productDtoResp));
        assertThat("Product Name Exists.", is(actual.getMessage()));
    }

    @Test
    void InsertProduct_WhenCategoriesNull_ShouldReturnProductDto() {
        when(productsRepositories.findByName(productDtoResp.getName())).thenReturn(null);
        when(categoriesRepositories.findByName(productDtoResp.getCategories())).thenReturn(null);
        ObjectNotFoundException actual = Assertions.assertThrows(ObjectNotFoundException.class, 
                () -> productsServiceImpl.insertProduct(productDtoResp));
        assertThat("Cannot Find Category Name: " + productDtoResp.getCategories(), is(actual.getMessage()));
    }

    @Test
    void UpdateProductInformation_WhenDataValid_ShouldReturnProductResp() {

        when(productsRepositories.findById(productDtoResp.getId())).thenReturn(Optional.of(product));
        when(categoriesRepositories.findByName(productDtoResp.getCategories())).thenReturn(category);
        when(productMapper.mapEntityToDto(product)).thenReturn(productDtoResp);

        ProductDto actual = productsServiceImpl.updateProductInformation(productDtoResp);
        verify(product).setName(productDtoResp.getName());
        verify(product).setPrice(productDtoResp.getPrice());
        verify(product).setCategories(category);
        verify(product).setImages(productDtoResp.getImages());

        assertThat(productDtoResp, is(actual));
    }

    @Test
    void UpdateProductInformation_WhenCategoryNull_ShouldThrowObjectNotFoundException(){
        when(productsRepositories.findById(productDtoResp.getId())).thenReturn(Optional.of(product));
        when(categoriesRepositories.findByName(productDtoResp.getCategories())).thenReturn(null);
        ObjectNotFoundException acutal = Assertions.assertThrows(ObjectNotFoundException.class, ()-> productsServiceImpl.updateProductInformation(productDtoResp));
        assertThat("Cannot Found Category Name: " + productDtoResp.getCategories(), is(acutal.getMessage()));
    }

    @Test
    void UpdateProductInformation_WhenProductNull_ShouldThrowObjectNotFoundException(){
        when(productsRepositories.findById(productDtoResp.getId())).thenReturn(Optional.empty());
        ObjectNotFoundException acutal = Assertions.assertThrows(ObjectNotFoundException.class, ()-> productsServiceImpl.updateProductInformation(productDtoResp));
        assertThat("Cannot Find Product With Id: " + productDtoResp.getId(), is(acutal.getMessage()));
    }

    @Test
    void UpdateProductStatus_WhenStatusData1_ShouldReturnProductDtoWithStatusActive() {
        when(productsRepositories.findById(productDtoResp.getId())).thenReturn(Optional.of(product));
        when(productsRepositories.save(product)).thenReturn(product);
        when(productMapper.mapEntityToDto(product)).thenReturn(productDtoResp);
        ProductDto actual = productsServiceImpl.updateProductStatus(productDtoResp.getId(), 1);
        verify(product).setStatus(StatusEnum.ACTIVE);
        assertThat(productDtoResp.getStatus(), is(actual.getStatus()));

    }

    @Test
    void UpdateProductStatus_WhenStatusData2_ShouldReturnProductDtoWithStatusDeactive() {
        when(productsRepositories.findById(productDtoResp.getId())).thenReturn(Optional.of(product));
        when(productsRepositories.save(product)).thenReturn(product);
        when(productMapper.mapEntityToDto(product)).thenReturn(productDtoResp);
        ProductDto actual = productsServiceImpl.updateProductStatus(productDtoResp.getId(), 2);
        verify(product).setStatus(StatusEnum.DEACTIVE);
        assertThat(productDtoResp.getStatus(), is(actual.getStatus()));
    }

    @Test
    void UpdateProductStatus_WhenStatusEqual0_ShouldThrowInformationNotValid() {
        when(productsRepositories.findById(productDtoResp.getId())).thenReturn(Optional.of(product));
        InformationNotValidException actual = Assertions.assertThrows(InformationNotValidException.class, ()-> productsServiceImpl.updateProductStatus(productDtoResp.getId(), 0));
        assertThat("Status Not Valid.", is(actual.getMessage()));
    }

    @Test
    void UpdateProductStatus_WhenStatusEqual3_ShouldThrowInformationNotValid() {
        when(productsRepositories.findById(productDtoResp.getId())).thenReturn(Optional.of(product));
        InformationNotValidException actual = Assertions.assertThrows(InformationNotValidException.class, ()-> productsServiceImpl.updateProductStatus(productDtoResp.getId(), 3));
        assertThat("Status Not Valid.", is(actual.getMessage()));
    }

    @Test
    void UpdateProductStatus_WhenProductNull_ShouldThrowObjectNotFoundException() {
        when(productsRepositories.findById(productDtoResp.getId())).thenReturn(Optional.empty());
        ObjectNotFoundException actual = Assertions.assertThrows(ObjectNotFoundException.class, 
                ()-> productsServiceImpl.updateProductStatus(productDtoResp.getId(), 0));
        assertThat("Cannot Find Product With Id: " + productDtoResp.getId(), is(actual.getMessage()));
    }
}
