package com.nash.assignment.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nash.assignment.modal.Categories;
import com.nash.assignment.repositories.CategoriesRepositories;

@Service
public class CategoriesServiceImpl implements CategoriesService {
    @Autowired
    CategoriesRepositories categoriesRepositories;

    @Override
    public Categories insertCategories(Categories categories) {
        Categories insert = categoriesRepositories.save(categories);
        return insert;
    }

    @Override
    public Iterable<Categories> getAllCategories() {
        Iterable<Categories> list = categoriesRepositories.findAll();
        return list;
    }

}
