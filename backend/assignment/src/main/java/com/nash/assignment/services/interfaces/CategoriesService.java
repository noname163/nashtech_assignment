package com.nash.assignment.services.interfaces;

import com.nash.assignment.modal.Category;

public interface CategoriesService {

    Category insertCategories(Category categories);

    Iterable<Category> getAllCategories();
}
