package com.nash.assignment.controllers;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.nash.assignment.services.CloudinaryServiceImpl;
import com.nash.assignment.services.FileServiceImpl;
import com.nash.assignment.services.ImageServiceImpl;

@RestController
@RequestMapping("/image")
public class ImageController {

    @Autowired 
    ImageServiceImpl imageServiceImpl;
    @Autowired 
    CloudinaryServiceImpl cloudinaryServiceImpl;
    @PostMapping(value = "/images") 
    public ResponseEntity<List<String>> uploadMultibleImages(MultipartFile[] multipartFiles) throws IOException{
        return ResponseEntity.status(HttpStatus.OK).body(
            cloudinaryServiceImpl.uploadImages(multipartFiles)
        );
    }
    
    @PostMapping(value = "") 
    public ResponseEntity<String> uploadImage(MultipartFile multipartFiles) throws IOException{
        return ResponseEntity.status(HttpStatus.OK).body(
            cloudinaryServiceImpl.uploadImage(multipartFiles)
        );
    }
}
