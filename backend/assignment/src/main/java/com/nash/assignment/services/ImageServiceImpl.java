package com.nash.assignment.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nash.assignment.modal.Image;
import com.nash.assignment.repositories.ImagesRepositories;
import com.nash.assignment.services.interfaces.ImageService;

@Service
public class ImageServiceImpl implements ImageService {

    @Autowired ImagesRepositories imagesRepositories;
    @Override
    public Image insertImage(Image imageValue) {
        Image image = imagesRepositories.save(imageValue);
        return image;
    }
    
}
