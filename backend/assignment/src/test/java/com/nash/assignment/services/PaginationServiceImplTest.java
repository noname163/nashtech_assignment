package com.nash.assignment.services;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

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

public class PaginationServiceImplTest {

    ProductRepositoriesPagination productRepositoriesPagination;
    AccountRepositoriesPagination accountRepositoriesPagination;
    OrderRepositoriesPagination orderRepositoriesPagination;
    OrderDetailRepositoriesPagination orderDetailRepositoriesPagination;
    ProductMapper productMapper;
    ModelMapper modelMapper;
    OrderMapper orderMapper;
    OrderDetailMapper orderDetailMapper;
    PaginationServiceImpl paginationServiceImpl;
    Pageable pageable;
    PageRequest pageRequest;
    int page;
    int itemDisplay;
    int dataFrom;
    int toData;


    @BeforeEach
    void setUp() {
        productRepositoriesPagination = mock(ProductRepositoriesPagination.class);
        accountRepositoriesPagination = mock(AccountRepositoriesPagination.class);
        orderRepositoriesPagination = mock(OrderRepositoriesPagination.class);
        orderDetailRepositoriesPagination = mock(OrderDetailRepositoriesPagination.class);
        productMapper = mock(ProductMapper.class);
        modelMapper = mock(ModelMapper.class);
        orderMapper = mock(OrderMapper.class);
        orderDetailMapper = mock(OrderDetailMapper.class);
        pageable = mock(Pageable.class);
        pageRequest = mock(PageRequest.class);
        paginationServiceImpl = new PaginationServiceImpl(productRepositoriesPagination, accountRepositoriesPagination,
                orderRepositoriesPagination, orderDetailRepositoriesPagination, productMapper, modelMapper, orderMapper,
                orderDetailMapper);
    }

    @Test
    void getAllProductPagination_ShouldReturnListOfProductDto() {
        ArgumentCaptor<Pageable> paginaton = ArgumentCaptor.forClass(Pageable.class);
        Product product = mock(Product.class);
        List<Product> productList = new ArrayList<>();
        productList.add(product);

        List<ProductDtoForUser> expect = new ArrayList<>();
        ProductDtoForUser productDtoForUser = mock(ProductDtoForUser.class);
        expect.add(productDtoForUser);

        Page<Product> page = new PageImpl<>(productList);

        when(productRepositoriesPagination.findAll(paginaton.capture())).thenReturn(page);
        when(productMapper.mapEntityToDto(product)).thenReturn(productDtoForUser);

        List<ProductDtoForUser> actual = paginationServiceImpl.getAllProductPagination(0, 3);
        assertThat(expect, is(actual));
    }

    @Test
    void getAllProductPagination_WhenPageEmpty_ShouldReturnEmptyList(){
        ArgumentCaptor<Pageable> paginaton = ArgumentCaptor.forClass(Pageable.class);
        Page<Product> page = new PageImpl<>(Collections.emptyList());
        List<ProductDtoForUser> expect = new ArrayList<>();
        when(productRepositoriesPagination.findAll(paginaton.capture())).thenReturn(page);

        List<ProductDtoForUser> actual = paginationServiceImpl.getAllProductPagination(0, 3);
        assertThat(expect, is(actual));
    }
    @Test
    void getAllAccountPagination_ShouldReturnListOfAccountDto() {
        ArgumentCaptor<Pageable> paginaton = ArgumentCaptor.forClass(Pageable.class);
        Account account = mock(Account.class);
        List<Account> accountList = new ArrayList<>();
        accountList.add(account);

        List<AccountDto> expect = new ArrayList<>();
        AccountDto accountDto = mock(AccountDto.class);
        expect.add(accountDto);

        Page<Account> page = new PageImpl<>(accountList);

        when(accountRepositoriesPagination.findAll(paginaton.capture())).thenReturn(page);
        when(modelMapper.map(account, AccountDto.class)).thenReturn(accountDto);

        List<AccountDto> actual = paginationServiceImpl.getAllAccountPagination(0, 3);
        assertThat(expect, is(actual));
    }

    @Test
    void getAllAccountPagination_WhenPageEmpty_ShouldReturnEmptyList(){
        ArgumentCaptor<Pageable> paginaton = ArgumentCaptor.forClass(Pageable.class);
        List<AccountDto> expect = new ArrayList<>();
        Page<Account> page = new PageImpl<>(Collections.emptyList());
        when(accountRepositoriesPagination.findAll(paginaton.capture())).thenReturn(page);

        List<AccountDto> actual = paginationServiceImpl.getAllAccountPagination(0, 3);
        assertThat(expect, is(actual));
    }
    @Test
    void getAllOrderDetailPagination_ShouldReturnListOfOrderDetailDto() {
        ArgumentCaptor<Pageable> paginaton = ArgumentCaptor.forClass(Pageable.class);
        OrderDetail orderDetail = mock(OrderDetail.class);
        List<OrderDetail> orderDetailList = new ArrayList<>();
        orderDetailList.add(orderDetail);

        List<OrderDetailDto> expect = new ArrayList<>();
        OrderDetailDto orderDetailDto = mock(OrderDetailDto.class);
        expect.add(orderDetailDto);

        Page<OrderDetail> page = new PageImpl<>(orderDetailList);

        when(orderDetailRepositoriesPagination.findAll(paginaton.capture())).thenReturn(page);
        when(orderDetailMapper.mapEntityToDto(orderDetail)).thenReturn(orderDetailDto);

        List<OrderDetailDto> actual = paginationServiceImpl.getAllOrderDetailPagination(0, 3);
        assertThat(expect, is(actual));
    }

    @Test
    void getAllOrderDetailPagination_WhenPageEmpty_ShouldReturnEmptyList(){
        ArgumentCaptor<Pageable> paginaton = ArgumentCaptor.forClass(Pageable.class);
        List<OrderDetailDto> expect = new ArrayList<>();
        Page<OrderDetail> page = new PageImpl<>(Collections.emptyList());
        when(orderDetailRepositoriesPagination.findAll(paginaton.capture())).thenReturn(page);

        List<OrderDetailDto> actual = paginationServiceImpl.getAllOrderDetailPagination(0, 3);
        assertThat(expect, is(actual));
    }
    @Test
    void getAllOrderPagination_ShouldReturnListOfOrderDto() {
        ArgumentCaptor<Pageable> paginaton = ArgumentCaptor.forClass(Pageable.class);
        Order order = mock(Order.class);
        List<Order> orderList = new ArrayList<>();
        orderList.add(order);

        List<OrderDto> expect = new ArrayList<>();
        OrderDto orderDto = mock(OrderDto.class);
        expect.add(orderDto);

        Page<Order> page = new PageImpl<>(orderList);

        when(orderRepositoriesPagination.findAll(paginaton.capture())).thenReturn(page);
        when(orderMapper.mapOrderEntityToDto(order)).thenReturn(orderDto);

        List<OrderDto> actual = paginationServiceImpl.getAllOrderPagination(0, 3);
        assertThat(expect, is(actual));
    }

    @Test
    void getAllOrderPagination_WhenPageEmpty_ShouldReturnEmptyList(){
        ArgumentCaptor<Pageable> paginaton = ArgumentCaptor.forClass(Pageable.class);
        List<OrderDto> expect = new ArrayList<>();
        Page<Order> page = new PageImpl<>(Collections.emptyList());
        when(orderRepositoriesPagination.findAll(paginaton.capture())).thenReturn(page);

        List<OrderDto> actual = paginationServiceImpl.getAllOrderPagination(0, 3);
        assertThat(expect, is(actual));
    }
}
