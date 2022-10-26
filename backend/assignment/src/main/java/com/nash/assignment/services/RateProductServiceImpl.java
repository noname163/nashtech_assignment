package com.nash.assignment.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nash.assignment.constant.RatingStatus;
import com.nash.assignment.dto.RateProductDto;
import com.nash.assignment.dto.request.OrderDetailDto;
import com.nash.assignment.dto.request.OrderDto;
import com.nash.assignment.exceptions.InformationNotValidException;
import com.nash.assignment.mapper.OrderDetailMapper;
import com.nash.assignment.mapper.OrderMapper;
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
    private OrderMapper orderMapper;
    private OrderDetailMapper orderDetailMapper;

    @Autowired
    public RateProductServiceImpl(AccountRepositories accountRepositories,
            RateProductRepositories rateProductRepositories, ProductsRepositories productsRepositories,
            OrderDetailRepositories orderDetailRepositories, OrderRepositories orderRepositories,
            RateProductMapper rateProductMapper, OrderMapper orderMapper, OrderDetailMapper orderDetailMapper) {
        this.accountRepositories = accountRepositories;
        this.rateProductRepositories = rateProductRepositories;
        this.productsRepositories = productsRepositories;
        this.orderDetailRepositories = orderDetailRepositories;
        this.orderRepositories = orderRepositories;
        this.rateProductMapper = rateProductMapper;
        this.orderMapper = orderMapper;
        this.orderDetailMapper = orderDetailMapper;
    }

    @Override
    public List<RateProductDto> insertRate(OrderDto orderDto) {
        List<RateProduct> rates = new ArrayList<>();
        Order order = orderMapper.mapDtoToEntity(orderDto);
        Account account = accountRepositories.findByEmail(orderDto.getEmail());
        List<OrderDetail> orderDetails = orderDto.getOrderDetails();
        // List<Product> products = orderDetailRepositories.findProductByOrder(order); 
        for (OrderDetail orderDetail : orderDetails) {
            RateProduct rate = new RateProduct();
            rate.setAccount(account);
            rate.setOrderDetail(orderDetail);
            rate.setProduct(orderDetail.getProduct());
            rate.setStatus(RatingStatus.RATING);
            rates.add(rate);
        }
        rates = rateProductRepositories.saveAll(rates);
        return rateProductMapper.mapEntityToDto(rates);
    }

    public RateProductDto updateRate(OrderDetailDto orderDetailDto, int rateStar) {
        Optional<OrderDetail> orderDetailOtp = orderDetailRepositories.findById(orderDetailDto.getId());
        OrderDetail orderDetail = orderDetailOtp.get();
        RateProduct rate = rateProductRepositories.findByOrderDetail(orderDetail);
        rate.setRate(rateStar);
        rate.setStatus(RatingStatus.RATED);
        rate = rateProductRepositories.save(rate);
        return rateProductMapper.mapEntityToDto(rate);
    }

    public List<RateProductDto> getRate(){
        return rateProductMapper.mapEntityToDto(rateProductRepositories.findAll());
    }
}
