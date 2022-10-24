package com.nash.assignment.services.interfaces;

import com.nash.assignment.dto.request.OrderDto;

public interface OrderService {
    OrderDto insertOrder(OrderDto orderDto);

    OrderDto updateOrderStatus(int id, int status);

    OrderDto cancelOrder(int id);

    OrderDto confirmOrder(int id);
}
