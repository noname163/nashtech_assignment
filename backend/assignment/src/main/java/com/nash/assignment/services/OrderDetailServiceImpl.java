package com.nash.assignment.services;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nash.assignment.dto.request.OrderDetailDto;
import com.nash.assignment.dto.request.OrderDto;
import com.nash.assignment.exceptions.ObjectNotFoundException;
import com.nash.assignment.mapper.OrderDetailMapper;
import com.nash.assignment.modal.Account;
import com.nash.assignment.modal.Order;
import com.nash.assignment.modal.OrderDetail;
import com.nash.assignment.repositories.OrderDetailRepositories;
import com.nash.assignment.repositories.OrderRepositories;
import com.nash.assignment.services.interfaces.OrderDetailService;

@Service
public class OrderDetailServiceImpl implements OrderDetailService {

    @Autowired
    OrderDetailRepositories orderDetailRepositories;
    @Autowired
    OrderDetailMapper orderDetailMapper;
    @Autowired
    OrderRepositories orderRepositories;

    public OrderDetail insert(OrderDetail orderDetail) {
        return orderDetailRepositories.save(orderDetail);
    }

    public List<OrderDetailDto> getAllOrderDetail() {
        List<OrderDetail> orderDetails = orderDetailRepositories.findAll();
        List<OrderDetailDto> orderDetailDtos = orderDetailMapper.mapEntityToDto(orderDetails);
        return orderDetailDtos;
    }

    @Override
    public List<OrderDetailDto> insertOrderDetail(List<OrderDetailDto> orderDetailDtos, OrderDto orderDto) {

        int id = orderDto.getId();
        Optional<Order> orderOtp = orderRepositories.findById(id);
        if (orderOtp.isEmpty()) {
            throw new ObjectNotFoundException("Cannot Find Order With Id: " + id);
        }
        Order order = orderOtp.get();
        List<OrderDetail> orderDetails = orderDetailMapper.mapDtoToEntity(orderDetailDtos);
        for (OrderDetail orderDetail : orderDetails) {
            orderDetail.setOrder(order);
            orderDetail.setAccount(order.getAccount());
        }
        orderDetailRepositories.saveAll(orderDetails);
        return orderDetailDtos;
    }

}
