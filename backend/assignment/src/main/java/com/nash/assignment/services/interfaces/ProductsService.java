package com.nash.assignment.services.interfaces;

import java.util.List;

import com.nash.assignment.dto.response.ProductDtoRes;
import com.nash.assignment.modal.Product;

public interface ProductsService {
    ProductDtoRes insertProduct(ProductDtoRes products, String categories);

    List<ProductDtoRes> getAllProducts();

    ProductDtoRes updateProductStatus(ProductDtoRes productValue, int statusValue);

    ProductDtoRes updateProductInformation(ProductDtoRes productValue);
}
