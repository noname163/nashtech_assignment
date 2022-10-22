package com.nash.assignment.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nash.assignment.dto.ProductDto;
import com.nash.assignment.mapper.ProductMapper;
import com.nash.assignment.modal.Image;
import com.nash.assignment.modal.Product;
import com.nash.assignment.repositories.ImagesRepositories;
import com.nash.assignment.repositories.ProductsRepositories;
import com.nash.assignment.services.interfaces.ImageService;

@Service
public class ImageServiceImpl implements ImageService {

    @Autowired
    ImagesRepositories imagesRepositories;
    @Autowired
    ProductMapper productMapper;
    @Autowired
    ProductsRepositories productsRepositories;

    @Override
    public Image insertImage(Image imageValue) {
        Image image = imagesRepositories.save(imageValue);
        return image;
    }

    public Set<Image> insertMultipeImages(List<String> imageName, ProductDto productDto) {
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

}
