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
import com.nash.assignment.dto.ProductDtoForAdmin;
import com.nash.assignment.dto.response.ProductDtoForUser;
import com.nash.assignment.exceptions.InformationNotValidException;
import com.nash.assignment.exceptions.ObjectExistException;
import com.nash.assignment.exceptions.ObjectNotFoundException;
import com.nash.assignment.mapper.ImageMapper;
import com.nash.assignment.mapper.ProductMapper;
import com.nash.assignment.mapper.ProductMapperForAdmin;
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

    ProductDtoForUser productDtoResp;

    ProductDtoForAdmin productDtoForAdmin;

    ProductMapperForAdmin productMapperForAdmin;

    Product product;

    ImageMapper imageMapper;

    @BeforeEach
    void setUpObject() {
        productMapper = mock(ProductMapper.class);
        productMapperForAdmin = mock(ProductMapperForAdmin.class);
        imageMapper = mock(ImageMapper.class);
        category = mock(Category.class);
        productsRepositories = mock(ProductsRepositories.class);
        productDtoResp = mock(ProductDtoForUser.class);
        productDtoForAdmin = mock(ProductDtoForAdmin.class);
        product = mock(Product.class);
        categoriesRepositories = mock(CategoriesRepositories.class);
        productsServiceImpl = new ProductsServiceImpl(productsRepositories, categoriesRepositories, productMapper, imageMapper, productMapperForAdmin);
    }

    @Test
    void getAllProducts_ShouldReturnListProductDto() {
        List<ProductDtoForUser> expectedList = new ArrayList<>();
        List<Product> productList = new ArrayList<>();
        productList.add(product);
        when(productsRepositories.findAll()).thenReturn(productList);
        when(productMapper.mapEntityToDto(product)).thenReturn(productDtoResp);
        expectedList.add(productDtoResp);

        List<ProductDtoForUser> actual = productsServiceImpl.getAllProducts();
        assertThat(expectedList, is(actual));

    }

    @Test
    void insertProduct_WhenDataValid_ShouldReturnProductDtoForAdmin() {
        ProductDtoForAdmin expected = mock(ProductDtoForAdmin.class);
        when(categoriesRepositories.findByName(productDtoForAdmin.getCategories())).thenReturn(category);
        when(productMapperForAdmin.mapDtoToEntity(expected)).thenReturn(product);
        when(productsRepositories.save(product)).thenReturn(product);
        when(productMapperForAdmin.mapEntityToDto(product)).thenReturn(expected);
        ProductDtoForAdmin actual = productsServiceImpl.insertProduct(expected);
        verify(expected).setStatus(StatusEnum.ACTIVE);
        assertThat(expected, is(actual));
    }

    @Test
    void insertProduct_WhenProductExist_ShouldThrowObjectNotFoundException() {
        when(productsRepositories.findByName(productDtoForAdmin.getName())).thenReturn(product);
        ObjectExistException actual = Assertions.assertThrows(ObjectExistException.class,
                () -> productsServiceImpl.insertProduct(productDtoForAdmin));
        assertThat("Product Name Exists.", is(actual.getMessage()));
    }

    @Test
    void insertProduct_WhenCategoriesNull_ShouldReturnProductDto() {
        when(productsRepositories.findByName(productDtoForAdmin.getName())).thenReturn(null);
        when(categoriesRepositories.findByName(productDtoForAdmin.getCategories())).thenReturn(null);
        ObjectNotFoundException actual = Assertions.assertThrows(ObjectNotFoundException.class,
                () -> productsServiceImpl.insertProduct(productDtoForAdmin));
        assertThat("Cannot Find Category Name: " + productDtoForAdmin.getCategories(), is(actual.getMessage()));
    }

    @Test
    void updateProductInformation_WhenDataValid_ShouldReturnProductResp() {

        when(productsRepositories.findByName(productDtoForAdmin.getName())).thenReturn(product);
        when(categoriesRepositories.findByName(productDtoForAdmin.getCategories())).thenReturn(category);
        when(productMapperForAdmin.mapEntityToDto(product)).thenReturn(productDtoForAdmin);

        ProductDtoForAdmin actual = productsServiceImpl.updateProductInformation(productDtoForAdmin);
        verify(product).setName(productDtoForAdmin.getName());
        verify(product).setPrice(productDtoForAdmin.getPrice());
        verify(product).setCategories(category);
        verify(product).setImages(imageMapper.mapImageProductDtoToEntity(productDtoForAdmin.getImages()));

        assertThat(productDtoForAdmin, is(actual));
    }

    @Test
    void updateProductInformation_WhenCategoryNull_ShouldThrowObjectNotFoundException() {
        when(productsRepositories.findByName(productDtoForAdmin.getName())).thenReturn(product);
        when(categoriesRepositories.findByName(productDtoForAdmin.getCategories())).thenReturn(null);
        ObjectNotFoundException acutal = Assertions.assertThrows(ObjectNotFoundException.class, () -> productsServiceImpl.updateProductInformation(productDtoForAdmin));
        assertThat("Cannot Found Category Name: " + productDtoForAdmin.getCategories(), is(acutal.getMessage()));
    }

    @Test
    void updateProductInformation_WhenProductNull_ShouldThrowObjectNotFoundException() {
        when(productsRepositories.findByName(productDtoForAdmin.getName())).thenReturn(null);
        ObjectNotFoundException acutal = Assertions.assertThrows(ObjectNotFoundException.class, () -> productsServiceImpl.updateProductInformation(productDtoForAdmin));
        assertThat("Cannot Find Product With Name: " + productDtoForAdmin.getName(), is(acutal.getMessage()));
    }

    @Test
    void updateProductStatus_WhenStatusData1_ShouldReturnProductDtoWithStatusActive() {
        when(productsRepositories.findById(productDtoForAdmin.getId())).thenReturn(Optional.of(product));
        when(productsRepositories.save(product)).thenReturn(product);
        when(productMapperForAdmin.mapEntityToDto(product)).thenReturn(productDtoForAdmin);
        ProductDtoForAdmin actual = productsServiceImpl.updateProductStatus(productDtoForAdmin.getId(), 1);
        verify(product).setStatus(StatusEnum.ACTIVE);
        assertThat(productDtoForAdmin.getStatus(), is(actual.getStatus()));

    }

    @Test
    void updateProductStatus_WhenStatusData2_ShouldReturnProductDtoWithStatusDeactive() {
        when(productsRepositories.findById(productDtoForAdmin.getId())).thenReturn(Optional.of(product));
        when(productsRepositories.save(product)).thenReturn(product);
        when(productMapperForAdmin.mapEntityToDto(product)).thenReturn(productDtoForAdmin);
        ProductDtoForAdmin actual = productsServiceImpl.updateProductStatus(productDtoForAdmin.getId(), 2);
        verify(product).setStatus(StatusEnum.DEACTIVE);
        assertThat(productDtoForAdmin.getStatus(), is(actual.getStatus()));
    }

    @Test
    void updateProductStatus_WhenStatusEqual0_ShouldThrowInformationNotValid() {
        when(productsRepositories.findById(productDtoResp.getId())).thenReturn(Optional.of(product));
        InformationNotValidException actual = Assertions.assertThrows(InformationNotValidException.class, () -> productsServiceImpl.updateProductStatus(productDtoResp.getId(), 0));
        assertThat("Status Not Valid.", is(actual.getMessage()));
    }

    @Test
    void updateProductStatus_WhenStatusEqual3_ShouldThrowInformationNotValid() {
        when(productsRepositories.findById(productDtoResp.getId())).thenReturn(Optional.of(product));
        InformationNotValidException actual = Assertions.assertThrows(InformationNotValidException.class, () -> productsServiceImpl.updateProductStatus(productDtoResp.getId(), 3));
        assertThat("Status Not Valid.", is(actual.getMessage()));
    }

    @Test
    void updateProductStatus_WhenProductNull_ShouldThrowObjectNotFoundException() {
        when(productsRepositories.findById(productDtoResp.getId())).thenReturn(Optional.empty());
        ObjectNotFoundException actual = Assertions.assertThrows(ObjectNotFoundException.class,
                () -> productsServiceImpl.updateProductStatus(productDtoResp.getId(), 0));
        assertThat("Cannot Find Product With Id: " + productDtoResp.getId(), is(actual.getMessage()));
    }
}
