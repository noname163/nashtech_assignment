package com.nash.assignment.services;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mockito;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import com.nash.assignment.dto.AccountDto;
import com.nash.assignment.dto.ProductDtoForAdmin;
import com.nash.assignment.dto.response.ProductDtoForUser;
import com.nash.assignment.mapper.ImageMapper;
import com.nash.assignment.mapper.ProductMapper;
import com.nash.assignment.modal.Account;
import com.nash.assignment.modal.Image;
import com.nash.assignment.modal.Product;
import com.nash.assignment.repositories.AccountRepositories;
import com.nash.assignment.repositories.ImagesRepositories;
import com.nash.assignment.repositories.ProductsRepositories;

public class ImageServiceImplTest {

    private ImageServiceImpl imageServiceImpl;
    private ImagesRepositories imagesRepositories;
    private ImageMapper imageMapper;
    private ProductMapper productMapper;
    private AccountRepositories accountRepositories;
    private ProductsRepositories productsRepositories;
    @Captor
    ArgumentCaptor<Image> imageCaptor = ArgumentCaptor.forClass(Image.class);
    @Captor
    ArgumentCaptor<Set<Image>> imagesCaptor = ArgumentCaptor.forClass(HashSet.class);

    @BeforeEach
    void setUpBeforeTest() {
        imagesRepositories = mock(ImagesRepositories.class);
        imageMapper = mock(ImageMapper.class);
        accountRepositories = mock(AccountRepositories.class);
        productsRepositories = mock(ProductsRepositories.class);
        productMapper = mock(ProductMapper.class);

        imageServiceImpl = new ImageServiceImpl(imagesRepositories, productMapper, productsRepositories,
                accountRepositories, imageMapper);
    }

    @Test
    void insertAvatar_WhenImageValueNull_ShouldReturnNull() {
        AccountDto accountDto = mock(AccountDto.class);
        Image actual = imageServiceImpl.insertAvatar(null, accountDto);
        assertThat(null, is(actual));
    }

    @Test
    void insertAvatar_WhenImageValueEmpty_ShouldReturnNull() {
        AccountDto accountDto = mock(AccountDto.class);
        Image actual = imageServiceImpl.insertAvatar("", accountDto);
        assertThat(null, is(actual));
    }

    @Test
    void insertAvatar_WhenImageDataValid_ShouldReturnImage() {
        AccountDto accountDto = new AccountDto();
        Account account = mock(Account.class);
        String imageUrl = "image";

        when(accountRepositories.findByPhoneNumber(accountDto.getPhoneNumber())).thenReturn(account);
        Image actual = imageServiceImpl.insertAvatar(imageUrl, accountDto);
        verify(imagesRepositories).save(imageCaptor.capture());
        Image image = imageCaptor.getValue();
        assertThat(image, is(actual));
    }

    @Test
    void insertImage() {
        Image image = new Image();
        image.setUrl("Test");
        
        when(imagesRepositories.save(image)).thenReturn(image);
        Image actual = imageServiceImpl.insertImage(image);
        assertThat(actual, is(image));
    }

    @Test
    void insertMultipeImages_WhenImageNull_ShouldReturnNull() {
        List<String> imageName = null;
        ProductDtoForAdmin productDto = mock(ProductDtoForAdmin.class);
        List<Image> expected = new ArrayList<>();
        List<Image> actual = imageServiceImpl.insertMultipeImages(imageName, productDto);
        assertThat(actual, is(expected));
    }

    @Test
    void insertMultipeImages_WhenDataValid_ShouldReturnSetImage() {
        List<String> imageName = new ArrayList<>();
        ProductDtoForAdmin productDto = new ProductDtoForAdmin();
        imageName.add("test");

        Product product = mock(Product.class);
        ArgumentCaptor<List<Image>> images = ArgumentCaptor.forClass(ArrayList.class);

        when(productsRepositories.findByName(productDto.getName())).thenReturn(product);
        when(imagesRepositories.saveAll(images.capture())).thenReturn(images.capture());

        List<Image> actual = imageServiceImpl.insertMultipeImages(imageName, productDto);
        List<Image> expected = images.getValue();
        
        verify(imagesRepositories).saveAll(images.capture());
        assertThat(actual, is(expected));

    }
}
