package com.nash.assignment.modal;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nash.assignment.constant.OrderStatus;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @SequenceGenerator(name = "orders_sequence", sequenceName = "orders_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "orders_sequence")

    private int id;
    @Column(nullable = true, unique = false, length = 300)
    private String orderDate;
    @Column(nullable = true, unique = false, length = 300)
    private String deliveryDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = true, unique = false, length = 300)
    private OrderStatus status;

    @ManyToOne(fetch = FetchType.EAGER, optional = true, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "account_id")
    private Account account;

    @JsonIgnore
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "order", cascade = CascadeType.REFRESH)
    private List<OrderDetail> orderDetails = new ArrayList<>();

    public Order() {
    }

    public Order(String orderDate, String deliveryDate, Account account) {
        this.orderDate = orderDate;
        this.deliveryDate = deliveryDate;
        this.account = account;
    }

    public int getId() {
        return id;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    @JsonIgnore
    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public List<OrderDetail> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<OrderDetail> orderDetails) {
        this.orderDetails = orderDetails;
    }

    public void setId(int id) {
        this.id = id;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Order [id= " + id + ", account= " + account + ", deliveryDate= " + deliveryDate + ", orderDate= "
                + orderDate + "]";
    }
}
