package com.nash.assignment.repositories;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nash.assignment.modal.Image;
import com.nash.assignment.modal.Product;

public interface ImagesRepositories extends JpaRepository<Image, Long> {

    Set<Image> findByProduct(Product product);
}
