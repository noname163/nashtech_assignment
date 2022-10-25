package com.nash.assignment.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nash.assignment.dto.RateProductDto;
import com.nash.assignment.exceptions.InformationNotValidException;
import com.nash.assignment.mapper.RateProductMapper;
import com.nash.assignment.modal.Account;
import com.nash.assignment.modal.Order;
import com.nash.assignment.modal.OrderDetail;
import com.nash.assignment.modal.Product;
import com.nash.assignment.modal.RateProduct;
import com.nash.assignment.repositories.AccountRepositories;
import com.nash.assignment.repositories.OrderDetailRepositories;
import com.nash.assignment.repositories.OrderRepositories;
import com.nash.assignment.repositories.ProductsRepositories;
import com.nash.assignment.repositories.RateProductRepositories;
import com.nash.assignment.services.interfaces.RateProductService;

@Service
public class RateProductServiceImpl implements RateProductService {
    private AccountRepositories accountRepositories;
    private RateProductRepositories rateProductRepositories;
    private ProductsRepositories productsRepositories;
    private OrderDetailRepositories orderDetailRepositories;
    private RateProductMapper rateProductMapper;
    private OrderRepositories orderRepositories;

    
    @Autowired
    public RateProductServiceImpl(AccountRepositories accountRepositories,
            RateProductRepositories rateProductRepositories, ProductsRepositories productsRepositories,
            OrderDetailRepositories orderDetailRepositories,OrderRepositories orderRepositories,RateProductMapper rateProductMapper) {
        this.accountRepositories = accountRepositories;
        this.rateProductRepositories = rateProductRepositories;
        this.productsRepositories = productsRepositories;
        this.orderDetailRepositories = orderDetailRepositories;
        this.orderRepositories = orderRepositories;
        this.rateProductMapper = rateProductMapper;
    }



    @Override
    public RateProductDto insertRate(RateProductDto rateProductDto) {
        Account account = accountRepositories.findByEmail(rateProductDto.getEmail());
        Product product = productsRepositories.findByName(rateProductDto.getProduct());
        OrderDetail orderDetail = orderDetailRepositories.findByAccountAndProduct(account, product);
        if(orderDetail==null){
            throw new InformationNotValidException("You Must Order Product To Rating.");
        }
        RateProduct rateProduct = rateProductMapper.mapDtoToEntity(rateProductDto);
        rateProduct.setOrderDetail(orderDetail);
        rateProduct = rateProductRepositories.save(rateProduct);
        return rateProductMapper.mapEntityToDto(rateProduct);
    }
    
}
