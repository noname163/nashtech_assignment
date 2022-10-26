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
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.nash.assignment.constant.StatusEnum;

@Table
@Entity
public class Product {
    @Id
    @SequenceGenerator(name = "product_sequence", sequenceName = "product_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "product_sequence")

    private long id;

    @Column(nullable = true, unique = false, length = 300)
    private String name;
    @Column(nullable = true, unique = false, length = 300)
    private String price;

    @Column(unique = false)
    @Lob
    private String description;

    @Column(unique = false)
    private String createdDate;
    @Column(unique = false)
    private String updateDate;

    @Column(nullable = true, unique = false)
    @Enumerated(EnumType.STRING)
    private StatusEnum status;


    @ManyToOne(fetch = FetchType.EAGER, optional = true, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "categories_id")
    private Category categories;

    @JsonIgnore
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "product", cascade = CascadeType.REFRESH)
    private Set<RateProduct> rate = new HashSet<>();

    @JsonIgnore
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "product", cascade = CascadeType.REFRESH)
    private List<Image> images = new ArrayList<>();

    public Product() {
    }


    public Product(String name, String price, StatusEnum status, Category categories) {
        this.name = name;
        this.price = price;
        this.status = status;
        this.categories = categories;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public StatusEnum getStatus() {
        return status;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }

    public Category getCategories() {
        return categories;
    }

    public void setCategories(Category categories) {
        this.categories = categories;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }


    public String getDescription() {
        return description;
    }


    public void setDescription(String description) {
        this.description = description;
    }


    public String getCreatedDate() {
        return createdDate;
    }


    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }


    public String getUpdateDate() {
        return updateDate;
    }


    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    

    

}
