package com.nash.assignment.dto.response;

import java.util.HashSet;
import java.util.Set;

import com.nash.assignment.constant.StatusEnum;
import com.nash.assignment.modal.Category;
import com.nash.assignment.modal.Image;

public class ProductDtoRes {
    private long id;

    private String name;
    private String price;



    private StatusEnum status;


    private Category categories;


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
}
