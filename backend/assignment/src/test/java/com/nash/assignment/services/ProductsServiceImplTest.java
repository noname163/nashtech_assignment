package com.nash.assignment.services;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.nash.assignment.constant.ProductStatus;
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

    ImageServiceImpl imageServiceImpl;



    @BeforeEach
    void setUpObject() {
        productMapper = mock(ProductMapper.class);
        productMapperForAdmin = mock(ProductMapperForAdmin.class);
        imageMapper = mock(ImageMapper.class);
        category = mock(Category.class);
        productsRepositories = mock(ProductsRepositories.class);
        productDtoResp = mock(ProductDtoForUser.class);
        product = mock(Product.class);
        categoriesRepositories = mock(CategoriesRepositories.class);
        imageServiceImpl = mock(ImageServiceImpl.class);
        productDtoForAdmin = new ProductDtoForAdmin();
        productDtoForAdmin.setId(1l);
        productDtoForAdmin.setName("test");

        productsServiceImpl = new ProductsServiceImpl(productsRepositories, categoriesRepositories, productMapper, imageMapper, productMapperForAdmin, imageServiceImpl);
    }

    @Test
    void getAllProducts_ShouldReturnListProductDto() {
        List<ProductDtoForUser> expectedList = new ArrayList<>();
        List<Product> productList = new ArrayList<>();
        productList.add(product);

        when(productsRepositories.findByStatus(ProductStatus.AVAILABLE)).thenReturn(productList);
        when(productMapper.mapEntityToDto(product)).thenReturn(productDtoResp);
        expectedList.add(productDtoResp);

        List<ProductDtoForUser> actual = productsServiceImpl.getAllProductsAvailble();
        verify(productMapper).mapEntityToDto(product);

        assertThat(actual, is(expectedList));

    }
    @Test
    void getAllProducts_ShouldReturnListProductDtoForAdmmin() {
        List<ProductDtoForAdmin> expectedList = new ArrayList<>();
        List<Product> productList = new ArrayList<>();
        productList.add(product);

        when(productsRepositories.findAll()).thenReturn(productList);
        when(productMapperForAdmin.mapEntityToDto(product)).thenReturn(productDtoForAdmin);
        expectedList.add(productDtoForAdmin);

        List<ProductDtoForAdmin> actual = productsServiceImpl.getAllProductsAdmin();
        verify(productMapperForAdmin).mapEntityToDto(product);

        assertThat(actual, is(expectedList));

    }

    @Test
    void insertProduct_WhenDataValid_ShouldReturnProductDtoForAdmin() {
        ProductDtoForAdmin expected = mock(ProductDtoForAdmin.class);
        List<String> images = mock(List.class);
        LocalDate date = LocalDate.now();

        when(categoriesRepositories.findByName(productDtoForAdmin.getCategories())).thenReturn(category);
        when(productMapperForAdmin.mapDtoToEntity(expected)).thenReturn(product);
        when(productMapperForAdmin.mapEntityToDto(product)).thenReturn(expected);
        when(expected.getImages()).thenReturn(images);
        when(expected.getImages().isEmpty()).thenReturn(false);
        when(productsRepositories.save(product)).thenReturn(product);

        ProductDtoForAdmin actual = productsServiceImpl.insertProduct(expected);

        verify(expected).setStatus(ProductStatus.AVAILABLE);
        verify(productsRepositories).save(product);
        verify(product).setCreatedDate(date.toString());
        assertThat(expected, is(actual));
    }

    @Test
    void insertProduct_WhenProductExist_ShouldThrowObjectNotFoundException() {
        when(productsRepositories.findByName("test")).thenReturn(product);
        ObjectExistException actual = Assertions.assertThrows(ObjectExistException.class,
                () -> productsServiceImpl.insertProduct(productDtoForAdmin));
        assertThat("Product Name Exists.", is(actual.getMessage()));
    }

    @Test
    void insertProduct_WhenImageNull_ShouldReturnProductDto() {
        ProductDtoForAdmin productDtoForAdmin = mock(ProductDtoForAdmin.class);
        when(productsRepositories.findByName("test")).thenReturn(null);
        when(categoriesRepositories.findByName(productDtoForAdmin.getCategories())).thenReturn(category);
        when(productDtoForAdmin.getImages()).thenReturn(null);

        ObjectNotFoundException actual = Assertions.assertThrows(ObjectNotFoundException.class,
                () -> productsServiceImpl.insertProduct(productDtoForAdmin));
                assertThat("Image Null" , is(actual.getMessage()));
    }
    @Test
    void insertProduct_WhenImageListEmpty_ShouldReturnProductDto() {
        List<String> images = mock(List.class);
        ProductDtoForAdmin productDtoForAdmin = mock(ProductDtoForAdmin.class);
        when(productsRepositories.findByName("test")).thenReturn(null);
        when(categoriesRepositories.findByName(productDtoForAdmin.getCategories())).thenReturn(category);
        when(productDtoForAdmin.getImages()).thenReturn(images);
        when(productDtoForAdmin.getImages().isEmpty()).thenReturn(true);

        ObjectNotFoundException actual = Assertions.assertThrows(ObjectNotFoundException.class,
                () -> productsServiceImpl.insertProduct(productDtoForAdmin));
        assertThat("Image Null" , is(actual.getMessage()));
    }
    @Test
    void insertProduct_WhenCategoriesNull_ShouldReturnProductDto() {
        when(productsRepositories.findByName("test")).thenReturn(null);
        when(categoriesRepositories.findByName(productDtoForAdmin.getCategories())).thenReturn(null);

        ObjectNotFoundException actual = Assertions.assertThrows(ObjectNotFoundException.class,
                () -> productsServiceImpl.insertProduct(productDtoForAdmin));
        assertThat("Cannot Find Category Name: " + productDtoForAdmin.getCategories(), is(actual.getMessage()));
    }

    @Test
    void updateProductInformation_WhenDataValid_ShouldReturnProductResp() {

        when(productsRepositories.findById(1l)).thenReturn(Optional.of(product));
        when(categoriesRepositories.findByName(productDtoForAdmin.getCategories())).thenReturn(category);
        when(productMapperForAdmin.mapEntityToDto(product)).thenReturn(productDtoForAdmin);

        ProductDtoForAdmin actual = productsServiceImpl.updateProductInformation(productDtoForAdmin);
        verify(product).setName("test");
        verify(product).setPrice(productDtoForAdmin.getPrice());
        verify(product).setCategories(category);
        verify(product).setImages(imageMapper.mapImageProductDtoToEntity(productDtoForAdmin.getImages(),product));

        assertThat(productDtoForAdmin, is(actual));
    }

    @Test
    void updateProductInformation_WhenCategoryNull_ShouldThrowObjectNotFoundException() {
        when(productsRepositories.findById(1l)).thenReturn(Optional.of(product));
        when(categoriesRepositories.findByName(productDtoForAdmin.getCategories())).thenReturn(null);

        ObjectNotFoundException acutal = Assertions.assertThrows(ObjectNotFoundException.class, () -> productsServiceImpl.updateProductInformation(productDtoForAdmin));

        assertThat("Cannot Found Category Name: " + productDtoForAdmin.getCategories(), is(acutal.getMessage()));
    }

    @Test
    void updateProductInformation_WhenProductNull_ShouldThrowObjectNotFoundException() {
        when(productsRepositories.findById(1l)).thenReturn(Optional.empty());

        ObjectNotFoundException acutal = Assertions.assertThrows(ObjectNotFoundException.class, () -> productsServiceImpl.updateProductInformation(productDtoForAdmin));

        assertThat("Cannot Find Product With Id: " + productDtoForAdmin.getId(), is(acutal.getMessage()));
    }

    @Test
    void updateProductStatus_WhenStatusData1_ShouldReturnProductDtoWithStatusActive() {
        when(productsRepositories.findById(1l)).thenReturn(Optional.of(product));
        when(productsRepositories.save(product)).thenReturn(product);
        when(productMapperForAdmin.mapEntityToDto(product)).thenReturn(productDtoForAdmin);

        ProductDtoForAdmin actual = productsServiceImpl.updateProductStatus(1l, ProductStatus.AVAILABLE);
        
        verify(product).setStatus(ProductStatus.AVAILABLE);
        assertThat(productDtoForAdmin.getStatus(), is(actual.getStatus()));

    }

    @Test
    void updateProductStatus_WhenStatusData2_ShouldReturnProductDtoWithStatusDeactive() {
        when(productsRepositories.findById(1l)).thenReturn(Optional.of(product));
        when(productsRepositories.save(product)).thenReturn(product);
        when(productMapperForAdmin.mapEntityToDto(product)).thenReturn(productDtoForAdmin);
       
        ProductDtoForAdmin actual = productsServiceImpl.updateProductStatus(1l, ProductStatus.UNAVAILABLE);
        
        verify(product).setStatus(ProductStatus.UNAVAILABLE);
        assertThat(productDtoForAdmin.getStatus(), is(actual.getStatus()));
    }

    @Test
    void updateProductStatus_WhenProductNull_ShouldThrowObjectNotFoundException() {
        when(productsRepositories.findById(productDtoResp.getId())).thenReturn(Optional.empty());
        ObjectNotFoundException actual = Assertions.assertThrows(ObjectNotFoundException.class,
                () -> productsServiceImpl.updateProductStatus(productDtoResp.getId(), ProductStatus.AVAILABLE));
        assertThat("Cannot Find Product With Id: " + productDtoResp.getId(), is(actual.getMessage()));
    }

    @Test
    void getProductByCategories_WhenCategoryNull_ShouldThrowObjectNotFoundException(){
        when(categoriesRepositories.findByName("test")).thenReturn(null);
        ObjectNotFoundException actual = Assertions.assertThrows(ObjectNotFoundException.class, ()->productsServiceImpl.getProductByCategories("test"));
        assertThat(actual.getMessage(), is("Cannot Find Category Name: test"));
    }
    @Test
    void getProductByCategories_WhenCategoryValid_ShouldReturnListProductDtoForUser(){
        List<Product> productList = mock(List.class);
        List<ProductDtoForUser> expected = mock(List.class);
        when(categoriesRepositories.findByName("test")).thenReturn(category);
        when(productsRepositories.findByCategories(category)).thenReturn(productList);
        when(productMapper.mapEntityToDto(productList)).thenReturn(expected);
        List<ProductDtoForUser> actual = productsServiceImpl.getProductByCategories("test");
        assertThat(actual, is(expected));
    }

    @Test
    void findProductByName_ShouldReturnListProductDtoForUse(){
        List<Product> product = mock(List.class);
        List<ProductDtoForUser> expected = mock(List.class);
        when(productsRepositories.findByNameContainingIgnoreCaseAndStatus("test", ProductStatus.AVAILABLE)).thenReturn(product);
        when(productMapper.mapEntityToDto(product)).thenReturn(expected);
        List<ProductDtoForUser> actual = productsServiceImpl.findProductByName("test");
        assertThat(actual, is(expected));
    }
    @Test
    void findProductByName_WhenProductListNull_ShouldReturnEmpty(){
        List<Product> product = mock(List.class);
        List<ProductDtoForUser> expected =  Collections.emptyList();
        when(productsRepositories.findByNameContainingIgnoreCaseAndStatus("test", ProductStatus.AVAILABLE)).thenReturn(product);
        List<ProductDtoForUser> actual = productsServiceImpl.findProductByName("test");
        assertThat(actual, is(expected));
    }
    @Test
    void findProductByName_WhenEmpty_ShouldReturnNull(){
        List<Product> product = Collections.emptyList();
        List<ProductDtoForUser> expected = Collections.emptyList();
        when(productsRepositories.findByNameContainingIgnoreCaseAndStatus("test", ProductStatus.AVAILABLE)).thenReturn(product);
        List<ProductDtoForUser> actual = productsServiceImpl.findProductByName("test");
        assertThat(actual, is(expected));
    }

}
