package com.nash.assignment.controllers.admin;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import com.nash.assignment.dto.request.OrderDto;
import com.nash.assignment.services.OrderServiceImpl;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;



@Controller
@RequestMapping("/orders")

public class OrderController {
    OrderServiceImpl orderServiceImpl;

    @GetMapping()
    public ResponseEntity<List<OrderDto>> getMethodName() {
        return orderServiceImpl.getAllOrder()
    }
    
}
