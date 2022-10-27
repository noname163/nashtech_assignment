package com.nash.assignment.services.interfaces;

import java.util.List;

import com.nash.assignment.dto.request.OrderDetailDto;
import com.nash.assignment.dto.request.OrderDto;

public interface OrderDetailService {

    List<OrderDetailDto> insertOrderDetail(List<OrderDetailDto> orderDetailDtos, OrderDto orderDto);

}
