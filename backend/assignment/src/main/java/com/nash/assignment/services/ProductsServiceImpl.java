package com.nash.assignment.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nash.assignment.constant.StatusEnum;
import com.nash.assignment.modal.Category;
import com.nash.assignment.modal.Product;
import com.nash.assignment.modal.Status;
import com.nash.assignment.repositories.CategoriesRepositories;
import com.nash.assignment.repositories.ProductsRepositories;
import com.nash.assignment.repositories.StatusRepositories;
import com.nash.assignment.services.interfaces.ProductsService;

@Service
public class ProductsServiceImpl implements ProductsService {

    @Autowired
    ProductsRepositories productsRepositories;
    @Autowired
    StatusRepositories statusRepositories;
    @Autowired
    CategoriesRepositories categoriesRepositories;

    @Override
    public Product insertProduct(Product products, String categoriesValue) {
        if (categoriesValue == null || products.getName() == null || products.getPrice() == null
                || products.getStatus() == null || products.getCategories().getName().equals("")
                || products.getPrice().equals("") || products.getStatus().getStatus().equals("")
                || categoriesValue.equals("")) {
            throw new RuntimeException("Missing Information When Insert Product.");
        }
        if (productsRepositories.findByName(products.getName()) != null) {
            throw new RuntimeException("Product Name Exists.");
        }
        Status status = statusRepositories.findByStatus(StatusEnum.ACTIVE.name());
        if (status == null) {
            throw new RuntimeException("Cannot Find Status Name: " + StatusEnum.ACTIVE.name());
        }
        Category categories = categoriesRepositories.findByName(categoriesValue);
        if (categories == null) {
            throw new RuntimeException("Cannot Find Status Name: " + categoriesValue);
        }
        products.setStatus(status);
        products.setCategories(categories);
        products = productsRepositories.save(products);
        return products;
    }

    @Override
    public Iterable<Product> getAllProducts() {
        Iterable<Product> list = productsRepositories.findAll();
        return list;
    }

    @Override
    public Product updateProductStatus(Product productValue) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Product updateProductInformation(Product productValue) {
        // TODO Auto-generated method stub
        return null;
    }

}
