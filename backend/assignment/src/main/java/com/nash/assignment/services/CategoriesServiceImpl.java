package com.nash.assignment.services;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nash.assignment.dto.request.CategoriesDto;
import com.nash.assignment.exceptions.InformationNotValidException;
import com.nash.assignment.exceptions.ObjectNotFoundException;
import com.nash.assignment.modal.Category;
import com.nash.assignment.repositories.CategoriesRepositories;
import com.nash.assignment.services.interfaces.CategoriesService;
import java.util.Collections;

@Service
public class CategoriesServiceImpl implements CategoriesService {

    CategoriesRepositories categoriesRepositories;

    ModelMapper modelMapper;

    @Autowired
    public CategoriesServiceImpl(CategoriesRepositories categoriesRepositories, ModelMapper modelMapper) {
        this.categoriesRepositories = categoriesRepositories;
        this.modelMapper = modelMapper;
    }

    @Override
    public Category insertCategories(Category categories) {
        return categoriesRepositories.save(categories);
    }

    @Override
    public List<Category> getAllCategories() {
        List<Category> list = categoriesRepositories.findAll();
        if (list.isEmpty()) {
            return Collections.emptyList();
        }
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

    public CategoriesDto updateCategoryDescription(String name, String description) {
        Category category = categoriesRepositories.findByName(name);
        if (category == null) {
            throw new ObjectNotFoundException("Cannot Find Category Name: " + name);
        }
        category.setDescription(description);
        categoriesRepositories.save(category);
        return modelMapper.map(category, CategoriesDto.class);
    }

}
