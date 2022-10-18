package com.nash.assignment.modal;

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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table
public class OrderDetail {
    @Id
    @SequenceGenerator(name = "orderDetail_sequence", sequenceName = "orderDetail_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "orderDetail_sequence")

    private int id;

    @Column(unique = false, nullable = true, length = 300)
    private int quantity;


    @ManyToOne(fetch = FetchType.EAGER, optional = true, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "product_id")
    private Product product;

    // @JsonManagedReference
    // @ManyToOne(fetch = FetchType.EAGER, optional = true, cascade =
    // CascadeType.REFRESH)
    // @JoinColumn(name = "price_id")
    // private Prices price;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER, optional = true, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "categoriy_id")
    private Category categories;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER, optional = true, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "order_id")
    private Order order;

    @Column(nullable = true, unique = false, length = 300)
    private int total;

    public OrderDetail() {
    }

    public OrderDetail(int quantity, Product product, Category categories, Order order, int total) {
        this.quantity = quantity;
        this.product = product;
        this.categories = categories;
        this.order = order;
        this.total = total;
    }

    public int getId() {
        return id;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    // public Prices getPrice() {
    // return price;
    // }

    // public void setPrice(Prices price) {
    // this.price = price;
    // }

    public Category getCategories() {
        return categories;
    }

    public void setCategories(Category categories) {
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
