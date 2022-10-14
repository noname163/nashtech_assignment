package com.nash.assignment.service;

import com.nash.assignment.model.Categories;

public interface CategoriesService {
    Categories insertCategories(Categories categories);

    Iterable<Categories> getAllCategories();
}
