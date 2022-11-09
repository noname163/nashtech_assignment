package com.nash.assignment.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nash.assignment.constant.RatingStatus;
import com.nash.assignment.dto.RateProductDto;
import com.nash.assignment.dto.request.OrderDto;
import com.nash.assignment.exceptions.ObjectNotFoundException;
import com.nash.assignment.mapper.OrderMapper;
import com.nash.assignment.mapper.RateProductMapper;
import com.nash.assignment.modal.Account;
import com.nash.assignment.modal.Order;
import com.nash.assignment.modal.OrderDetail;
import com.nash.assignment.modal.RateProduct;
import com.nash.assignment.repositories.AccountRepositories;
import com.nash.assignment.repositories.OrderDetailRepositories;
import com.nash.assignment.repositories.RateProductRepositories;
import com.nash.assignment.services.interfaces.RateProductService;

@Service
public class RateProductServiceImpl implements RateProductService {

    private AccountRepositories accountRepositories;
    private RateProductRepositories rateProductRepositories;
    private OrderDetailRepositories orderDetailRepositories;
    private RateProductMapper rateProductMapper;
    private OrderMapper orderMapper;

    
    @Autowired
    public RateProductServiceImpl(AccountRepositories accountRepositories,
            RateProductRepositories rateProductRepositories, OrderDetailRepositories orderDetailRepositories,
            RateProductMapper rateProductMapper, OrderMapper orderMapper) {
        this.accountRepositories = accountRepositories;
        this.rateProductRepositories = rateProductRepositories;
        this.orderDetailRepositories = orderDetailRepositories;
        this.rateProductMapper = rateProductMapper;
        this.orderMapper = orderMapper;
    }

    @Override
    public List<RateProductDto> insertRate(OrderDto orderDto) {
        List<RateProduct> rates = new ArrayList<>();
        Order order = orderMapper.mapDtoToEntity(orderDto);
        List<OrderDetail> orderDetails = orderDetailRepositories.findByOrder(order);
        if(orderDetails == null){
            throw new ObjectNotFoundException("Cannot Find Order Id: " + order.getId());
        }
        Account account = accountRepositories.findByEmail(orderDto.getEmail());
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

    public RateProductDto updateRate(int orderDetailId, int rateStar) {
        RateProduct rate = rateProductRepositories.findByOrderDetailId(orderDetailId);
        rate.setRate(rateStar);
        rate.setStatus(RatingStatus.RATED);
        rate = rateProductRepositories.save(rate);
        return rateProductMapper.mapEntityToDto(rate);
    }

    public List<RateProductDto> getRate() {
        return rateProductMapper.mapEntityToDto(rateProductRepositories.findAll());
    }
}
