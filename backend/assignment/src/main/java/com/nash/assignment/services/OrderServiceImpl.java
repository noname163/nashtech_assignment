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

    @Autowired
    OrderRepositories orderRepositories;
    @Autowired
    OrderMapper orderMapper;
    @Autowired
    HttpServletRequest request;
    @Autowired
    AccountRepositories accountRepositories;

    public Order insert(Order order) {
        return orderRepositories.save(order);
    }

    public List<OrderDto> getAllOrder() {
        List<Order> orders = orderRepositories.findAll();
        return orderMapper.mapEntityToDto(orders);
    }
    public List<OrderDto> getAllOrderByAccount() {
        HttpSession session = request.getSession();
        String email = (String) session.getAttribute("email");
        Account account = accountRepositories.findByEmail(email);
        List<Order> orders = orderRepositories.findByAccount(account);
        return orderMapper.mapEntityToDto(orders);
    }

    @Override
    public OrderDto insertOrder(OrderDto orderDto) {
        HttpSession session = request.getSession();
        String email = (String) session.getAttribute("email");
        orderDto.setEmail(email);
        orderDto.setStatus(OrderStatus.PENDING);
        Order order = orderMapper.mapDtoToEntity(orderDto);
        LocalDate date = LocalDate.now();
        order.setOrderDate(date.toString());
        order = orderRepositories.save(order);
        return orderMapper.mapOrderEntityToDto(order);
    }

    public OrderDto insertOrder2() {
        OrderDto orderDto = new OrderDto();
        HttpSession session = request.getSession();
        String email = (String) session.getAttribute("email");
        orderDto.setEmail(email);
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

}
