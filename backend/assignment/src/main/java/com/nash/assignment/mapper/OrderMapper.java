package com.nash.assignment.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nash.assignment.dto.request.OrderDto;
import com.nash.assignment.modal.Account;
import com.nash.assignment.modal.Order;
import com.nash.assignment.repositories.AccountRepositories;

@Component
public class OrderMapper {

    @Autowired
    AccountRepositories accountRepositories;
    @Autowired OrderDetailMapper orderDetailMapper;

    public OrderDto mapOrderEntityToDto(Order order) {
        OrderDto orderDto = new OrderDto();
        orderDto.setId(order.getId());
        orderDto.setEmail(order.getAccount().getEmail());
        orderDto.setDeliveryDate(order.getDeliveryDate());
        orderDto.setOrderDate(order.getOrderDate());
        orderDto.setStatus(order.getStatus());
        if (order != null) {
            orderDto.setOrderDetails(orderDetailMapper.mapEntityToDto(order.getOrderDetails()));
        }
        return orderDto;
    }

    public Order mapDtoToEntity(OrderDto orderDto) {
        Order order = new Order();
        Account account = accountRepositories.findByEmail(orderDto.getEmail());
        order.setId(orderDto.getId());
        order.setAccount(account);
        order.setDeliveryDate(orderDto.getDeliveryDate());
        order.setOrderDate(order.getOrderDate());
        if (orderDto.getOrderDetails() != null) {
            order.setOrderDetails(orderDetailMapper.mapDtoToEntity(orderDto.getOrderDetails()));
        }
        order.setStatus(orderDto.getStatus());
        return order;
    }

    public List<Order> mapDtoToEntity(List<OrderDto> orderDtos) {
        return orderDtos.stream().map(order -> mapDtoToEntity(order))
                .collect(Collectors.toList());
    }

    public List<OrderDto> mapEntityToDto(List<Order> orders) {
        return orders.stream().map(order -> mapOrderEntityToDto(order))
                .collect(Collectors.toList());
    }
}
