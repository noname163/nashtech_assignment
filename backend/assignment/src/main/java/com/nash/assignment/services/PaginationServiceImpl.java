package com.nash.assignment.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.nash.assignment.dto.response.ProductDtoForUser;
import com.nash.assignment.mapper.ProductMapper;
import com.nash.assignment.modal.Product;
import com.nash.assignment.repositories.ProductRepositoriesPagination;

@Service
public class PaginationServiceImpl {

    @Autowired ProductRepositoriesPagination productRepositoriesPagination;
    @Autowired ProductMapper productMapper;

    public List<ProductDtoForUser> getAllProductPagination(int page, int itemDisplay){
        int dataFrom = page==0 ? 0: page+itemDisplay;
        int toData = dataFrom+ 50;
        Pageable paginaton = PageRequest.of(dataFrom, toData);
        List<ProductDtoForUser> result = new ArrayList<>();
        Page<Product> productList = productRepositoriesPagination.findAll(paginaton);
        for (Product product : productList) {
            result.add(productMapper.mapEntityToDto(product));
        }
        return result;
    }
}
