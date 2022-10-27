package com.nash.assignment.mapper;

import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Generated;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nash.assignment.dto.response.ProductDtoForUser;
import com.nash.assignment.modal.Product;
import com.nash.assignment.repositories.CategoriesRepositories;

@Generated(value = "org.mapstruct.ap.MappingProcessor", date = "2021-03-11T19:21:44+0100", comments = "version: 1.4.2.Final, compiler: javac, environment: Java 13.0.2 (Oracle Corporation)")
@Component
public class ProductMapper {

    @Autowired
    CategoriesRepositories categoriesRepositories;
    @Autowired
    ImageMapper imageMapper;

    public ProductDtoForUser mapEntityToDto(Product product) {
        ProductDtoForUser productDto = new ProductDtoForUser();
        productDto.setId(product.getId());
        productDto.setCategories(product.getCategories().getName());
        productDto.setImages(imageMapper.mapEntityToImageProductDto(product.getImages()));
        productDto.setName(product.getName());
        productDto.setPrice(product.getPrice());
        productDto.setStatus(product.getStatus());
        productDto.setDescription(product.getDescription());
        return productDto;
    }

    public Product mapDtoToEntity(ProductDtoForUser productDto) {
        Product product = new Product();
        product.setId(productDto.getId());
        product.setCategories(categoriesRepositories.findByName(productDto.getCategories()));
        product.setName(productDto.getName());
        product.setImages(imageMapper.mapImageProductDtoToEntity(productDto.getImages()));
        product.setPrice(productDto.getPrice());
        product.setStatus(productDto.getStatus());
        product.setDescription(productDto.getDescription());
        return product;
    }

    public List<Product> mapDtoToEntity(List<ProductDtoForUser> productDtoForUsers) {
        return productDtoForUsers.stream().map(product -> mapDtoToEntity(product)).collect(Collectors.toList());
    }

    public List<ProductDtoForUser> mapEntityToDto(List<Product> products) {
        return products.stream().map(product -> mapEntityToDto(product)).collect(Collectors.toList());
    }
}
