package com.nash.assignment.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nash.assignment.modal.Products;
import com.nash.assignment.repositories.ProductsRepositories;

@Service
public class ProductsServiceImpl implements ProductsService {

    @Autowired
    ProductsRepositories productsRepositories;

    @Override
    public Products insertProduct(Products products) {
        Products insert = productsRepositories.save(products);
        return insert;
    }

    @Override
    public Iterable<Products> getAllProducts() {
        Iterable<Products> list = productsRepositories.findAll();
        return list;
    }

}
