package com.nash.assignment.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nash.assignment.modal.Image;

public interface ImagesRepositories extends JpaRepository<Image,Long> {
    
}
