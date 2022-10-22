package com.nash.assignment.dto;

import com.nash.assignment.modal.Product;

public class ImageDto {
    private long id;


    private String url;


    private Product product;

    public ImageDto(String url, Product product) {
        this.url = url;
        this.product = product;
    }

    public ImageDto(String url) {
        this.url = url;
    }

    public ImageDto() {
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
}
