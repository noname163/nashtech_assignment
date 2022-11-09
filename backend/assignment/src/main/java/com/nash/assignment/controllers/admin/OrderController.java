package com.nash.assignment.controllers.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nash.assignment.dto.request.OrderDetailDto;
import com.nash.assignment.dto.request.OrderDto;
import com.nash.assignment.services.OrderServiceImpl;
import com.nash.assignment.services.PaginationServiceImpl;



@Controller
@RequestMapping("/admin/orders")
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
public class OrderController {
    @Autowired
    OrderServiceImpl orderServiceImpl;
    @Autowired
    PaginationServiceImpl paginationServiceImpl;

    @GetMapping()
    public ResponseEntity<List<OrderDto>> getAllOrder() {
        return ResponseEntity.status(HttpStatus.OK).body(
            orderServiceImpl.getAllOrder()
        );
    }
    @GetMapping("/{page}/{item}")
    public ResponseEntity<List<OrderDto>> getOrderPagination(@PathVariable int page, @PathVariable int item) {
        return ResponseEntity.status(HttpStatus.OK).body(
            paginationServiceImpl.getAllOrderPagination(page, item)
        );
    }
    @GetMapping("/order-detail/{page}/{item}")
    public ResponseEntity<List<OrderDetailDto>> getOrderDetailPagination(@PathVariable int page, @PathVariable int item) {
        return ResponseEntity.status(HttpStatus.OK).body(
            paginationServiceImpl.getAllOrderDetailPagination(page, item)
        );
    }

    @PatchMapping("/accept-order/{id}")
    public ResponseEntity<?> acceptedOrder(@PathVariable int id){
        orderServiceImpl.acceptedOrder(id);
        return ResponseEntity.noContent().build();
    }
    @PatchMapping("/delivery-order/{id}")
    public ResponseEntity<?> deliveryOrder(@PathVariable int id){
        orderServiceImpl.deliveryOrder(id);
        return ResponseEntity.noContent().build();
    }
    
}
