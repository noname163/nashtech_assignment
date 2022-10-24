package com.nash.assignment.controllers.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nash.assignment.dto.request.OrderDetailDto;
import com.nash.assignment.dto.request.OrderDto;
import com.nash.assignment.mapper.OrderDetailMapper;
import com.nash.assignment.mapper.OrderMapper;
import com.nash.assignment.services.OrderDetailServiceImpl;
import com.nash.assignment.services.OrderServiceImpl;

@Controller
@PreAuthorize("hasAuthority('ROLE_USER')")
@RequestMapping("/add-to-cart")
public class AddToCartController {
    @Autowired
    OrderDetailServiceImpl orderDetailServiceImpl;
    @Autowired
    OrderServiceImpl orderServiceImpl;
    @Autowired
    OrderDetailMapper orderDetailMapper;
    @Autowired
    OrderMapper orderMapper;

    @PostMapping()
    public ResponseEntity<OrderDto> addToCart(@RequestBody OrderDto orderDto) {
        OrderDto order = orderServiceImpl.insertOrder(orderDto);
        List<OrderDetailDto> orderDetail = orderDetailServiceImpl
                .insertOrderDetail(orderDetailMapper.mapEntityToDto(orderDto.getOrderDetails()), order);
        order.setOrderDetails(orderDetailMapper.mapDtoToEntity(orderDetail));
        return ResponseEntity.status(HttpStatus.CREATED).body(
                order);
    }

    @GetMapping(value = "/get-order")
    public ResponseEntity<List<OrderDto>> getOrder() {
        List<OrderDto> orders = orderServiceImpl.getAllOrder();
        return ResponseEntity.status(HttpStatus.OK).body(
                orders);
    }

    @GetMapping(value = "/get-order-detail")
    public ResponseEntity<List<OrderDetailDto>> getOrderDetail() {
        List<OrderDetailDto> orderDetails = orderDetailServiceImpl.getAllOrderDetail();
        return ResponseEntity.status(HttpStatus.OK).body(
                orderDetails);
    }

}
