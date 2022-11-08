package com.nash.assignment.modal;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(indexes = @Index(columnList = "url"))
public class Image {
    @Id
    @SequenceGenerator(name = "images_sequence", sequenceName = "images_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "images_sequence")

    private long id;

    @Column(nullable = true, unique = false, length = 200)
    private String url;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, optional = true, cascade = CascadeType.MERGE)
    @JoinColumn(name = "product_id")
    private Product product;

    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL)
    private Account avatar;

    

    public Image(String url, Account avatar) {
        this.url = url;
        this.avatar = avatar;
    }

    public Image(String url, Product product) {
        this.url = url;
        this.product = product;
    }

    public Image(String url) {
        this.url = url;
    }

    public Image() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Account getAvatar() {
        return avatar;
    }

    public void setAvatar(Account avatar) {
        this.avatar = avatar;
    }

}
