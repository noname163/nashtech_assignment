package com.nash.assignment.services.interfaces;

import java.util.List;

import com.nash.assignment.dto.ProductDto;
import com.nash.assignment.modal.Product;

public interface ProductsService {
    ProductDto insertProduct(ProductDto products);

    List<ProductDto> getAllProducts();

    ProductDto updateProductStatus(ProductDto productValue, int statusValue);

    ProductDto updateProductInformation(ProductDto productValue);
}
