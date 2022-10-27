package com.nash.assignment.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nash.assignment.modal.Product;

public class ImageProductDto {

    private String url;

    private Product product;

    public ImageProductDto(String url, Product product) {
        this.url = url;
        this.product = product;
    }

    public ImageProductDto(String url) {
        this.url = url;
    }

    public ImageProductDto() {
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @JsonIgnore
    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
