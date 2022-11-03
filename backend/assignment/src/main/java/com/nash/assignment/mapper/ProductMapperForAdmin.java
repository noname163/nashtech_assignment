package com.nash.assignment.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nash.assignment.dto.ProductDtoForAdmin;
import com.nash.assignment.modal.Product;
import com.nash.assignment.repositories.CategoriesRepositories;

@Component
public class ProductMapperForAdmin {

    @Autowired
    CategoriesRepositories categoriesRepositories;
    @Autowired
    ImageMapper imageMapper;

    public ProductDtoForAdmin mapEntityToDto(Product product) {
        ProductDtoForAdmin productDto = new ProductDtoForAdmin();
        productDto.setId(product.getId());
        productDto.setCategories(product.getCategories().getName());
        productDto.setImages(imageMapper.mapEntityToSting(product.getImages()));
        productDto.setName(product.getName());
        productDto.setPrice(product.getPrice());
        productDto.setStatus(product.getStatus());
        productDto.setDescription(product.getDescription());
        productDto.setCreatedDate(product.getCreatedDate());
        productDto.setUpdateDate(product.getUpdateDate());
        return productDto;
    }

    public Product mapDtoToEntity(ProductDtoForAdmin productDto) {
        Product product = new Product();
        product.setId(productDto.getId());
        product.setCategories(categoriesRepositories.findByName(productDto.getCategories()));
        product.setName(productDto.getName());
        product.setImages(imageMapper.mapImageProductDtoToEntity(productDto.getImages(), product));
        product.setPrice(productDto.getPrice());
        product.setStatus(productDto.getStatus());
        product.setDescription(productDto.getDescription());
        product.setCreatedDate(productDto.getCreatedDate());
        product.setUpdateDate(productDto.getUpdateDate());
        return product;
    }
}
