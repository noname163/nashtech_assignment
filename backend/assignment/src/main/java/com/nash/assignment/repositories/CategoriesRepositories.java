package com.nash.assignment.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nash.assignment.modal.Category;

@Repository
public interface CategoriesRepositories extends JpaRepository<Category, Long> {

    Category findByName(String name);
}
