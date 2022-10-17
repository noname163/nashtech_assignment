package com.nash.assignment.services;

import com.nash.assignment.modal.Product;

public interface ProductsService {
    Product insertProduct(Product products, String categories);

    Iterable<Product> getAllProducts();

    Product updateProductStatus(Product productValue);

    Product updateProductInformation(Product productValue);
}
