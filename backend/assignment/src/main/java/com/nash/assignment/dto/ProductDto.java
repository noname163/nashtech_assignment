package com.nash.assignment.dto;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import com.nash.assignment.constant.StatusEnum;
import com.nash.assignment.modal.Image;

import lombok.Builder;

// @Builder
public class ProductDto {
    private long id;
    @NotBlank(message= "Name Cannot Empty")
    private String name;
    @Min(value = 1, message = "Price Cannot Empty")
    private String price;



    private StatusEnum status;

    @NotBlank(message = "Categories Cannot Empty")
    private String categories;


    private Set<Image> images = new HashSet<>();



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
    

    public Set<Image> getImages() {
        return images;
    }

    public void setImages(Set<Image> images) {
        this.images = images;
    }

    public StatusEnum getStatus() {
        return status;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }

    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
