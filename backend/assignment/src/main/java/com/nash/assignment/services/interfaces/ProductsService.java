package com.nash.assignment.services.interfaces;

import java.util.List;

import com.nash.assignment.dto.ProductDtoForAdmin;
import com.nash.assignment.dto.response.ProductDtoForUser;
import com.nash.assignment.modal.Product;

public interface ProductsService {
    ProductDtoForAdmin insertProduct(ProductDtoForAdmin products);

    List<ProductDtoForUser> getAllProducts();

    ProductDtoForAdmin updateProductStatus(long id, int statusValue);

    ProductDtoForAdmin updateProductInformation(ProductDtoForAdmin productValue);
}
