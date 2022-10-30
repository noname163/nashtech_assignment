package com.nash.assignment.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nash.assignment.dto.request.OrderDetailDto;
import com.nash.assignment.modal.OrderDetail;
import com.nash.assignment.modal.Product;
import com.nash.assignment.repositories.ProductsRepositories;

@Component
public class OrderDetailMapper {

    @Autowired
    ProductsRepositories productsRepositories;
    @Autowired ProductMapper productMapper;

    public OrderDetail mapDtoToEnity(OrderDetailDto orderDetailDto) {
        OrderDetail orderDetail = new OrderDetail();
        if (orderDetailDto.getOrder() != null) {
            orderDetail.setOrder(orderDetailDto.getOrder());
        }
        orderDetail.setProduct(productMapper.mapDtoToEntity(orderDetailDto.getProduct()));
        orderDetail.setQuantity(orderDetailDto.getQuantity());
        orderDetail.setTotal(orderDetailDto.getTotal());
        return orderDetail;
    }

    public OrderDetailDto mapEntityToDto(OrderDetail orderDetail) {
        OrderDetailDto orderDetailDto = new OrderDetailDto();
        if (orderDetail.getOrder() != null) {
            orderDetailDto.setOrder(orderDetail.getOrder());
        }
        orderDetailDto.setId(orderDetail.getId());
        orderDetailDto.setProduct(productMapper.mapEntityToDto(orderDetail.getProduct()));
        orderDetailDto.setQuantity(orderDetail.getQuantity());
        orderDetailDto.setTotal(orderDetail.getTotal());
        return orderDetailDto;
    }

    public OrderDetail mapDtoToEntity(OrderDetailDto orderDetailDto, Product product){
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setProduct(product);
        if (orderDetailDto.getOrder() != null) {
            orderDetail.setOrder(orderDetailDto.getOrder());
        }
        orderDetail.setProduct(product);
        orderDetail.setQuantity(orderDetailDto.getQuantity());
        orderDetail.setTotal(orderDetailDto.getTotal());
        return orderDetail;
    }

    public List<OrderDetail> mapDtoToEntity(List<OrderDetailDto> orderDetailDtos) {
        return orderDetailDtos.stream().map((order -> mapDtoToEnity(order)))
                .collect(Collectors.toList());
    }
    

    public List<OrderDetailDto> mapEntityToDto(List<OrderDetail> orderDetails) {
        List<OrderDetailDto> result = orderDetails.stream().map(order -> 
        mapEntityToDto(order))
                .collect(Collectors.toList());
        return result;
    }
}
