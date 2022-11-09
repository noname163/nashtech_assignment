package com.nash.assignment.services.interfaces;

import java.util.List;

import com.nash.assignment.constant.ProductStatus;
import com.nash.assignment.dto.ProductDtoForAdmin;
import com.nash.assignment.dto.response.ProductDtoForUser;

public interface ProductsService {

    ProductDtoForAdmin insertProduct(ProductDtoForAdmin products);

    List<ProductDtoForUser> getAllProductsAvailble();

    ProductDtoForAdmin updateProductStatus(long id, ProductStatus statusValue);

    ProductDtoForAdmin updateProductInformation(ProductDtoForAdmin productValue);
}
