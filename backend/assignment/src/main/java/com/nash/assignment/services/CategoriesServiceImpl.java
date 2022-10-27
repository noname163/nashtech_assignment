package com.nash.assignment.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nash.assignment.dto.request.CategoriesDto;
import com.nash.assignment.exceptions.InformationNotValidException;
import com.nash.assignment.modal.Category;
import com.nash.assignment.repositories.CategoriesRepositories;
import com.nash.assignment.services.interfaces.CategoriesService;

@Service
public class CategoriesServiceImpl implements CategoriesService {

    @Autowired
    CategoriesRepositories categoriesRepositories;

    @Autowired
    ModelMapper modelMapper;

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

    public CategoriesDto insertCategory(CategoriesDto categoriesDto) {
        if (categoriesRepositories.findByName(categoriesDto.getName()) != null) {
            throw new InformationNotValidException("Already Exitts Categories Name: " + categoriesDto.getName());
        }
        Category category = modelMapper.map(categoriesDto, Category.class);
        category = categoriesRepositories.save(category);
        return modelMapper.map(category, CategoriesDto.class);
    }

}
