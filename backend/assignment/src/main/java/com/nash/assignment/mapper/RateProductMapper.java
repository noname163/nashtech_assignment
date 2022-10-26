package com.nash.assignment.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nash.assignment.dto.RateProductDto;
import com.nash.assignment.modal.Account;
import com.nash.assignment.modal.Product;
import com.nash.assignment.modal.RateProduct;
import com.nash.assignment.repositories.AccountRepositories;
import com.nash.assignment.repositories.ProductsRepositories;

@Component
public class RateProductMapper {

    @Autowired
    AccountRepositories accountRepositories;
    @Autowired
    ProductsRepositories productsRepositories;

    public RateProduct mapDtoToEntity(RateProductDto rateProductDto) {
        Product product = productsRepositories.findByName(rateProductDto.getProduct());
        Account account = accountRepositories.findByEmail(rateProductDto.getEmail());
        RateProduct rateProduct = new RateProduct();
        rateProduct.setRate(rateProductDto.getRate());
        rateProduct.setProduct(product);
        rateProduct.setAccount(account);
        rateProduct.setStatus(rateProductDto.getStatus());
        return rateProduct;
    }

    public RateProductDto mapEntityToDto(RateProduct rateProduct) {
        RateProductDto rateProductDto = new RateProductDto();
        rateProductDto.setRate(rateProduct.getRate());
        rateProductDto.setEmail(rateProduct.getAccount().getEmail());
        rateProductDto.setProduct(rateProduct.getProduct().getName());
        rateProductDto.setStatus(rateProduct.getStatus());
        return rateProductDto;
    }

    public List<RateProductDto> mapEntityToDto(List<RateProduct> rateProducts) {
        return rateProducts.stream().map(rateProduct -> mapEntityToDto(rateProduct)).collect(Collectors.toList());
    }
}
