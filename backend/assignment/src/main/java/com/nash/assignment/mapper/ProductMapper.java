package com.nash.assignment.mapper;

import javax.annotation.Generated;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nash.assignment.dto.ProductDto;
import com.nash.assignment.modal.Product;
import com.nash.assignment.repositories.CategoriesRepositories;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-03-11T19:21:44+0100",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 13.0.2 (Oracle Corporation)"
)
@Component
public class ProductMapper {
    @Autowired CategoriesRepositories categoriesRepositories;
    public ProductDto mapEntityToDto(Product product) {
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setCategories(product.getCategories().getName());
        productDto.setImages(product.getImages());
        productDto.setName(product.getName());
        productDto.setPrice(product.getPrice());
        productDto.setStatus(product.getStatus());
        return productDto ;
    }

    public Product mapDtoToEntity(ProductDto productDto){
        Product product = new Product();
        product.setId(productDto.getId());
        product.setCategories(categoriesRepositories.findByName(productDto.getCategories()));
        product.setName(productDto.getName());
        product.setImages(productDto.getImages());
        product.setPrice(productDto.getPrice());
        product.setStatus(productDto.getStatus());
        return product;
    }
}
