package com.nash.assignment.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table
public class OrderDetails {
    @Id
    @SequenceGenerator(name = "orderDetail_sequence", sequenceName = "orderDetail_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "orderDetail_sequence")

    private int id;

    @Column(unique = false, nullable = true, length = 300)
    private int quantity;

    @JsonManagedReference
    @ManyToOne(fetch = FetchType.EAGER, optional = true, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "product_id")
    private Products product;

    // @JsonManagedReference
    // @ManyToOne(fetch = FetchType.EAGER, optional = true, cascade =
    // CascadeType.REFRESH)
    // @JoinColumn(name = "price_id")
    // private Prices price;

    @JsonManagedReference
    @ManyToOne(fetch = FetchType.EAGER, optional = true, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "categoriy_id")
    private Categories categories;

    @JsonManagedReference
    @ManyToOne(fetch = FetchType.EAGER, optional = true, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "order_id")
    private Orders order;

    @Column(nullable = true, unique = false, length = 300)
    private int total;

    public OrderDetails() {
    }

    public OrderDetails(int quantity, Products product, Categories categories, Orders order, int total) {
        this.quantity = quantity;
        this.product = product;
        this.categories = categories;
        this.order = order;
        this.total = total;
    }

    public int getId() {
        return id;
    }

    public Orders getOrder() {
        return order;
    }

    public void setOrder(Orders order) {
        this.order = order;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Products getProduct() {
        return product;
    }

    public void setProduct(Products product) {
        this.product = product;
    }

    // public Prices getPrice() {
    // return price;
    // }

    // public void setPrice(Prices price) {
    // this.price = price;
    // }

    public Categories getCategories() {
        return categories;
    }

    public void setCategories(Categories categories) {
        this.categories = categories;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "OrderDetail [id= " + id + ", order= " + order + ", categories= " + categories
                + ", product= " + product
                + ", quantity= " + quantity + ", total= " + total + "]";
    }
}
