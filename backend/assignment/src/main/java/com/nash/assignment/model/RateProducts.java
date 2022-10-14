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
public class RateProducts {
    @Id
    @SequenceGenerator(name = "productRates_sequence", sequenceName = "productRates_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "productRates_sequence")
    private long id;

    @Column(nullable = true, unique = false, length = 6)
    private int rate;

    @JsonManagedReference
    @ManyToOne(fetch = FetchType.EAGER, optional = true, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "account_id")
    private Accounts account;

    @JsonManagedReference
    @ManyToOne(fetch = FetchType.EAGER, optional = true, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "product_id")
    private Products product;

    public RateProducts() {
    }

    public RateProducts(int rate, Accounts account, Products product) {
        this.rate = rate;
        this.account = account;
        this.product = product;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public Accounts getAccount() {
        return account;
    }

    public void setAccount(Accounts account) {
        this.account = account;
    }

    public Products getProduct() {
        return product;
    }

    public void setProduct(Products product) {
        this.product = product;
    }

}
