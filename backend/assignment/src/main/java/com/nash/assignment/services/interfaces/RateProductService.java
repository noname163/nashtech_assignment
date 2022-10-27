package com.nash.assignment.services.interfaces;

import java.util.List;

import com.nash.assignment.dto.RateProductDto;
import com.nash.assignment.dto.request.OrderDto;

public interface RateProductService {

    List<RateProductDto> insertRate(OrderDto orderDto);
}
