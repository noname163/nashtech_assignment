package com.nash.assignment.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nash.assignment.dto.AccountDto;
import com.nash.assignment.dto.ProductDtoForAdmin;
import com.nash.assignment.dto.response.ImageAccountDto;
import com.nash.assignment.dto.response.ProductDtoForUser;
import com.nash.assignment.mapper.ImageMapper;
import com.nash.assignment.mapper.ProductMapper;
import com.nash.assignment.modal.Account;
import com.nash.assignment.modal.Image;
import com.nash.assignment.modal.Product;
import com.nash.assignment.repositories.AccountRepositories;
import com.nash.assignment.repositories.ImagesRepositories;
import com.nash.assignment.repositories.ProductsRepositories;
import com.nash.assignment.services.interfaces.ImageService;

@Service
public class ImageServiceImpl implements ImageService {

    ImagesRepositories imagesRepositories;

    ProductMapper productMapper;

    ProductsRepositories productsRepositories;

    AccountRepositories accountRepositories;

    ImageMapper imageMapper;

    @Autowired
    public ImageServiceImpl(ImagesRepositories imagesRepositories, ProductMapper productMapper,
            ProductsRepositories productsRepositories, AccountRepositories accountRepositories,
            ImageMapper imageMapper) {
        this.imagesRepositories = imagesRepositories;
        this.productMapper = productMapper;
        this.productsRepositories = productsRepositories;
        this.accountRepositories = accountRepositories;
        this.imageMapper = imageMapper;
    }

    @Override
    public Image insertImage(Image imageValue) {
        Image image = imagesRepositories.save(imageValue);
        return image;
    }

    public Set<Image> insertMultipeImages(List<String> imageName, ProductDtoForAdmin productDto) {
        Set<Image> result = new HashSet<>();
        if (imageName == null) {
            return result;
        }
        Product product = productsRepositories.findByName(productDto.getName());
        for (String url : imageName) {
            Image image = new Image(url, product);
            imagesRepositories.save(image);
            result.add(image);
        }

        return result;
    }

    public Image insertAvatar(String imageValue, AccountDto accountDto) {
        if (imageValue == null || imageValue.equals("")) {
            return null;
        }
        Account account = accountRepositories.findByPhoneNumber(accountDto.getPhoneNumber());
        Image image = new Image();
        image.setUrl(imageValue);
        image.setAvatar(account);
        imagesRepositories.save(image);
        return image;
    }

}
