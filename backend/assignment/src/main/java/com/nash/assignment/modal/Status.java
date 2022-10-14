package com.nash.assignment.modal;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table
public class Status {
    @Id
    @SequenceGenerator(name = "status_sequence", sequenceName = "status_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "status_sequence")
    private long id;

    @Column(nullable = false, unique = false, length = 50)
    private String status;

    @JsonBackReference
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "status", cascade = CascadeType.PERSIST)
    private Set<Accounts> accounts = new HashSet<>();

    @JsonBackReference
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "status", cascade = CascadeType.PERSIST)
    private Set<Products> products = new HashSet<>();

    public Status() {
    }

    public Status(String status) {
        this.status = status;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Set<Accounts> getAccounts() {
        return accounts;
    }

    public void setAccounts(Set<Accounts> accounts) {
        this.accounts = accounts;
    }

    public Set<Products> getProducts() {
        return products;
    }

    public void setProducts(Set<Products> products) {
        this.products = products;
    }

}
