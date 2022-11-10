package com.nash.assignment.controllers.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nash.assignment.dto.request.OrderDetailDto;
import com.nash.assignment.dto.request.OrderDto;
import com.nash.assignment.mapper.OrderDetailMapper;
import com.nash.assignment.mapper.OrderMapper;
import com.nash.assignment.services.OrderDetailServiceImpl;
import com.nash.assignment.services.OrderServiceImpl;
import com.nash.assignment.services.RateProductServiceImpl;

@RestController
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
    @Autowired RateProductServiceImpl rateProductServiceImpl;

    @PostMapping()
    public ResponseEntity<OrderDto> addToCart(@RequestBody OrderDto orderDto) {
        OrderDto order = orderServiceImpl.insertOrder(orderDto);
        List<OrderDetailDto> orderDetail = orderDetailServiceImpl
                .insertOrderDetail(orderDto.getOrderDetails(), order);
        order.setOrderDetails(orderDetail);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                order);
    }

    @PatchMapping(value="/confirm-order/{id}")
    public ResponseEntity<OrderDto> confirmOrder(@PathVariable int id){
        OrderDto order = orderServiceImpl.confirmOrder(id);
        rateProductServiceImpl.insertRate(order);
        return ResponseEntity.status(HttpStatus.OK).body(
            order
        );
    }

    @GetMapping(value = "/get-order/{email}")
    public ResponseEntity<List<OrderDto>> getOrder(@PathVariable String email) {
        List<OrderDto> orders = orderServiceImpl.getAllOrderByAccount(email);
        return ResponseEntity.status(HttpStatus.OK).body(
                orders);
    }

    @GetMapping(value = "/get-order-detail")
    public ResponseEntity<List<OrderDetailDto>> getOrderDetail() {
        List<OrderDetailDto> orderDetails = orderDetailServiceImpl.getAllOrderDetailByAccount();
        return ResponseEntity.status(HttpStatus.OK).body(
                orderDetails);
    }
    @GetMapping(value = "/get-order-detail/{id}")
    public ResponseEntity<List<OrderDetailDto>> viewOrderDetail(@PathVariable int id) {
        List<OrderDetailDto> orderDetails = orderDetailServiceImpl.getAllOrderDetailByOrderId(id);
        return ResponseEntity.status(HttpStatus.OK).body(
                orderDetails);
    }

}
