package com.nash.assignment.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "orders")
public class Orders {
    @Id
    @SequenceGenerator(name = "orders_sequence", sequenceName = "orders_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "orders_sequence")

    private int id;
    @Column(nullable = true, unique = false, length = 300)
    private String orderDate;
    @Column(nullable = true, unique = false, length = 300)
    private String deliveryDate;

    @JsonManagedReference
    @ManyToOne(fetch = FetchType.EAGER, optional = true, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "account_id")
    private Accounts account;

    @JsonBackReference
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "order", cascade = CascadeType.PERSIST)
    private Set<OrderDetails> orderDetails = new HashSet<>();

    public Orders() {
    }

    public Orders(String orderDate, String deliveryDate, Accounts account) {
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

    public Accounts getAccount() {
        return account;
    }

    public void setAccount(Accounts account) {
        this.account = account;
    }

    public Set<OrderDetails> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(Set<OrderDetails> orderDetails) {
        this.orderDetails = orderDetails;
    }

    @Override
    public String toString() {
        return "Order [id= " + id + ", account= " + account + ", deliveryDate= " + deliveryDate + ", orderDate= "
                + orderDate + "]";
    }
}
