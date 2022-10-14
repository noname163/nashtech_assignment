package com.nash.assignment.service;

import com.nash.assignment.modal.Categories;

public interface CategoriesService {
    Categories insertCategories(Categories categories);

    Iterable<Categories> getAllCategories();
}
