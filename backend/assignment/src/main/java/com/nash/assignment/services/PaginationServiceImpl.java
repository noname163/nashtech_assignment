package com.nash.assignment.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;



import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.nash.assignment.dto.AccountDto;
import com.nash.assignment.dto.request.OrderDetailDto;
import com.nash.assignment.dto.request.OrderDto;
import com.nash.assignment.dto.response.ProductDtoForUser;
import com.nash.assignment.mapper.OrderDetailMapper;
import com.nash.assignment.mapper.OrderMapper;
import com.nash.assignment.mapper.ProductMapper;
import com.nash.assignment.modal.Account;
import com.nash.assignment.modal.Order;
import com.nash.assignment.modal.OrderDetail;
import com.nash.assignment.modal.Product;
import com.nash.assignment.repositories.pagination.AccountRepositoriesPagination;
import com.nash.assignment.repositories.pagination.OrderDetailRepositoriesPagination;
import com.nash.assignment.repositories.pagination.OrderRepositoriesPagination;
import com.nash.assignment.repositories.pagination.ProductRepositoriesPagination;

@Service
public class PaginationServiceImpl {

    
    ProductRepositoriesPagination productRepositoriesPagination;
    
    AccountRepositoriesPagination accountRepositoriesPagination;
    
    OrderRepositoriesPagination orderRepositoriesPagination;
    
    OrderDetailRepositoriesPagination orderDetailRepositoriesPagination;
    
    ProductMapper productMapper;
    
    ModelMapper modelMapper;
    
    OrderMapper orderMapper;
    
    OrderDetailMapper orderDetailMapper;

    @Autowired
    public PaginationServiceImpl(ProductRepositoriesPagination productRepositoriesPagination,
            AccountRepositoriesPagination accountRepositoriesPagination,
            OrderRepositoriesPagination orderRepositoriesPagination,
            OrderDetailRepositoriesPagination orderDetailRepositoriesPagination, ProductMapper productMapper,
            ModelMapper modelMapper, OrderMapper orderMapper, OrderDetailMapper orderDetailMapper) {
        this.productRepositoriesPagination = productRepositoriesPagination;
        this.accountRepositoriesPagination = accountRepositoriesPagination;
        this.orderRepositoriesPagination = orderRepositoriesPagination;
        this.orderDetailRepositoriesPagination = orderDetailRepositoriesPagination;
        this.productMapper = productMapper;
        this.modelMapper = modelMapper;
        this.orderMapper = orderMapper;
        this.orderDetailMapper = orderDetailMapper;
    }

    public List<ProductDtoForUser> getAllProductPagination(int page, int itemDisplay) {
        int dataFrom = page == 0 ? 0 : page + itemDisplay;
        int toData = dataFrom + 50;
        Pageable paginaton = PageRequest.of(dataFrom, toData);
        List<ProductDtoForUser> result = new ArrayList<>();
        Page<Product> productList = productRepositoriesPagination.findAll(paginaton);
        if(productList.isEmpty()){
            return Collections.emptyList();
        }
        for (Product product : productList) {
            result.add(productMapper.mapEntityToDto(product));
        }
        return result;
    }

    public List<AccountDto> getAllAccountPagination(int page, int itemDisplay) {
        int dataFrom = page == 0 ? 0 : page + itemDisplay;
        int toData = dataFrom + 50;
        Pageable paginaton = PageRequest.of(dataFrom, toData);
        List<AccountDto> result = new ArrayList<>();
        Page<Account> accountList = accountRepositoriesPagination.findAll(paginaton);
        if(accountList.isEmpty()){
            return Collections.emptyList();
        }
        for (Account account : accountList) {
            result.add(modelMapper.map(account, AccountDto.class));
        }
        return result;
    }
    public List<OrderDetailDto> getAllOrderDetailPagination(int page, int itemDisplay) {
        int dataFrom = page == 0 ? 0 : page + itemDisplay;
        int toData = dataFrom + 50;
        Pageable paginaton = PageRequest.of(dataFrom, toData);
        List<OrderDetailDto> result = new ArrayList<>();
        Page<OrderDetail> orderDetailList = orderDetailRepositoriesPagination.findAll(paginaton);
        if(orderDetailList.isEmpty()){
            return Collections.emptyList();
        }
        for (OrderDetail order : orderDetailList) {
            result.add(orderDetailMapper.mapEntityToDto(order));
        }
        return result;
    }
    public List<OrderDto> getAllOrderPagination(int page, int itemDisplay) {
        int dataFrom = page == 0 ? 0 : page + itemDisplay;
        int toData = dataFrom + 50;
        Pageable paginaton = PageRequest.of(dataFrom, toData);
        List<OrderDto> result = new ArrayList<>();
        Page<Order> orderList = orderRepositoriesPagination.findAll(paginaton);
        if(orderList.isEmpty()){
            return Collections.emptyList();
        }
        for (Order order : orderList) {
            result.add(orderMapper.mapOrderEntityToDto(order));
        }
        return result;
    }
}
