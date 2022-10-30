package com.nash.assignment.dto.request;

import com.nash.assignment.dto.response.ProductDtoForUser;
import com.nash.assignment.modal.Order;

public class OrderDetailDto {
    private int id;


    private int quantity;


    private ProductDtoForUser product;

 
    private Order order;

    private int total;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public ProductDtoForUser getProduct() {
        return product;
    }

    public void setProduct(ProductDtoForUser product) {
        this.product = product;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    

}
