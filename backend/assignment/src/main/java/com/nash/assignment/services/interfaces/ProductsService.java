package com.nash.assignment.services.interfaces;

import com.nash.assignment.modal.Product;

public interface ProductsService {
    Product insertProduct(Product products, String categories);

    Iterable<Product> getAllProducts();

    Product updateProductStatus(Product productValue);

    Product updateProductInformation(Product productValue);
}
