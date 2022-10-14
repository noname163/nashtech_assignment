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

@Table
@Entity
public class Categories {
    @Id
    @SequenceGenerator(name = "categories_sequence", sequenceName = "categories_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "categories_sequence")

    private long id;
    @Column(unique = false, nullable = true, length = 300)
    private String name;

    @JsonBackReference
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "categories", cascade = CascadeType.REFRESH)
    private Set<Products> product_id = new HashSet<>();

    public Categories() {
    }

    public Categories(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Products> getProduct_id() {
        return product_id;
    }

    public void setProduct_id(Set<Products> product_id) {
        this.product_id = product_id;
    }

}
