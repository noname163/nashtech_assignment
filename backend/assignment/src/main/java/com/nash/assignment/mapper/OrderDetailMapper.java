package com.nash.assignment.mapper;

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

    public OrderDetail mapDtoToEnity(OrderDetailDto orderDetailDto) {
        OrderDetail orderDetail = new OrderDetail();
        Product product = productsRepositories.findByName(orderDetailDto.getProduct());
        if(orderDetailDto.getOrder()!=null){
            orderDetail.setOrder(orderDetailDto.getOrder());
        }
        orderDetail.setProduct(product);
        orderDetail.setQuantity(orderDetailDto.getQuantity());
        orderDetail.setTotal(orderDetailDto.getTotal());
        return orderDetail;
    }

    public OrderDetailDto mapEntityToDto(OrderDetail orderDetail) {
        OrderDetailDto orderDetailDto = new OrderDetailDto();
        if(orderDetail.getOrder()!=null){
            orderDetailDto.setOrder(orderDetail.getOrder());
        }
        orderDetailDto.setProduct(orderDetail.getProduct().getName());
        orderDetailDto.setQuantity(orderDetail.getQuantity());
        orderDetailDto.setTotal(orderDetail.getTotal());
        return orderDetailDto;
    }

    public List<OrderDetail> mapDtoToEntity(List<OrderDetailDto> orderDetailDtos) {
        return orderDetailDtos.stream().map(order -> mapDtoToEnity(order))
                .collect(Collectors.toList());
    }

    public List<OrderDetailDto> mapEntityToDto(List<OrderDetail> orderDetails) {
        return orderDetails.stream().map(order -> mapEntityToDto(order))
                .collect(Collectors.toList());
    }
}
