package com.nash.assignment.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nash.assignment.constant.OrderStatus;
import com.nash.assignment.dto.request.OrderDto;
import com.nash.assignment.exceptions.InformationNotValidException;
import com.nash.assignment.exceptions.ObjectNotFoundException;
import com.nash.assignment.mapper.OrderMapper;
import com.nash.assignment.modal.Account;
import com.nash.assignment.modal.Order;
import com.nash.assignment.repositories.AccountRepositories;
import com.nash.assignment.repositories.OrderRepositories;
import com.nash.assignment.services.interfaces.OrderService;

@Service
public class OrderServiceImpl implements OrderService {

    OrderRepositories orderRepositories;
    OrderMapper orderMapper;
    HttpServletRequest request;
    AccountRepositories accountRepositories;


    @Autowired
    public OrderServiceImpl(OrderRepositories orderRepositories, OrderMapper orderMapper, HttpServletRequest request,
            AccountRepositories accountRepositories) {
        this.orderRepositories = orderRepositories;
        this.orderMapper = orderMapper;
        this.request = request;
        this.accountRepositories = accountRepositories;
    }
    public List<OrderDto> getAllOrder() {
        List<Order> orders = orderRepositories.findAll();
        return orderMapper.mapEntityToDto(orders);
    }
    public List<OrderDto> getAllOrderByAccount(String email) {
        Account account = accountRepositories.findByEmail(email);
        List<Order> orders = orderRepositories.findByAccount(account);
        return orderMapper.mapEntityToDto(orders);
    }

    @Override
    public OrderDto insertOrder(OrderDto orderDto) {
        orderDto.setStatus(OrderStatus.PENDING);
        Order order = orderMapper.mapDtoToEntity(orderDto);
        LocalDate date = LocalDate.now();
        order.setOrderDate(date.toString());
        order = orderRepositories.save(order);
        return orderMapper.mapOrderEntityToDto(order);
    }


    @Override
    public OrderDto updateOrderStatus(int id, OrderStatus status) {
        Optional<Order> orderOtp = orderRepositories.findById(id);
        OrderStatus statusEnum = null;
        if (orderOtp.isEmpty()) {
            throw new ObjectNotFoundException("Cannot Find Order With iD: " + id);
        }
        Order order = orderOtp.get();
        order.setStatus(statusEnum);
        order = orderRepositories.save(order);

        return orderMapper.mapOrderEntityToDto(order);
    }

    @Override
    public OrderDto cancelOrder(int id) {
        Optional<Order> orderOtp = orderRepositories.findById(id);
        if (orderOtp.isEmpty()) {
            throw new ObjectNotFoundException("Cannot Find Order With Id: " + id);
        }
        Order order = orderOtp.get();
        order.setStatus(OrderStatus.CANCEL);
        order = orderRepositories.save(order);
        return orderMapper.mapOrderEntityToDto(order);
    }

    @Override
    public OrderDto confirmOrder(int id) {
        Optional<Order> orderOtp = orderRepositories.findById(id);
        if (orderOtp.isEmpty()) {
            throw new ObjectNotFoundException("Cannot Find Order With Id: " + id);
        }
        Order order = orderOtp.get();
        order.setStatus(OrderStatus.SUCCESS);
        order = orderRepositories.save(order);
        return orderMapper.mapOrderEntityToDto(order);
    }

    public OrderDto acceptedOrder(int id){
        Optional<Order> orderOtp = orderRepositories.findById(id);
        if (orderOtp.isEmpty()) {
            throw new ObjectNotFoundException("Cannot Find Order With Id: " + id);
        }
        Order order = orderOtp.get();
        LocalDate date = LocalDate.now();
        order.setDeliveryDate(date.plusDays(7).toString());
        order.setStatus(OrderStatus.ACCEPT);
        order = orderRepositories.save(order);
        return orderMapper.mapOrderEntityToDto(order);
    }
    public OrderDto deliveryOrder(int id){
        Optional<Order> orderOtp = orderRepositories.findById(id);
        if (orderOtp.isEmpty()) {
            throw new ObjectNotFoundException("Cannot Find Order With Id: " + id);
        }
        Order order = orderOtp.get();
        LocalDate date = LocalDate.now();
        order.setDeliveryDate(date.toString());
        order.setStatus(OrderStatus.DELIVERY);
        order = orderRepositories.save(order);
        return orderMapper.mapOrderEntityToDto(order);
    }

}
