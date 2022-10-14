package com.nash.assignment.service;

import com.nash.assignment.modal.Products;

public interface ProductsService {
    Products insertProduct(Products products);

    Iterable<Products> getAllProducts();
}
