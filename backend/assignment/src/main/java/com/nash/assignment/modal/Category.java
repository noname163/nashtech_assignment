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
import javax.persistence.Index;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Table(indexes = @Index(columnList = "name"))
@Entity
public class Category {

    @Id
    @SequenceGenerator(name = "categories_sequence", sequenceName = "categories_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "categories_sequence")

    private long id;
    @Column( unique = true, nullable = true, length = 300)
    private String name;

    @Column(unique = false, columnDefinition = "text")
    private String description;

    @JsonIgnore
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "categories", cascade = CascadeType.REFRESH)
    private Set<Product> product_id = new HashSet<>();

    public Category() {
    }

    public Category(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Product> getProduct_id() {
        return product_id;
    }

    public void setProduct_id(Set<Product> product_id) {
        this.product_id = product_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
