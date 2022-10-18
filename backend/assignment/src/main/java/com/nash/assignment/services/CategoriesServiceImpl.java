package com.nash.assignment.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nash.assignment.modal.Category;
import com.nash.assignment.repositories.CategoriesRepositories;
import com.nash.assignment.services.interfaces.CategoriesService;

@Service
public class CategoriesServiceImpl implements CategoriesService {
    @Autowired
    CategoriesRepositories categoriesRepositories;

    @Override
    public Category insertCategories(Category categories) {
        Category insert = categoriesRepositories.save(categories);
        return insert;
    }

    @Override
    public Iterable<Category> getAllCategories() {
        Iterable<Category> list = categoriesRepositories.findAll();
        return list;
    }

}
