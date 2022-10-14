package com.nash.assignment.service;

import com.nash.assignment.model.Products;

public interface ProductsService {
    Products insertProduct(Products products);

    Iterable<Products> getAllProducts();
}
