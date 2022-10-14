package com.nash.assignment.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nash.assignment.model.Categories;

@Repository
public interface CategoriesRepositories extends JpaRepository<Categories, Long> {
    Categories findByName(String name);
}
