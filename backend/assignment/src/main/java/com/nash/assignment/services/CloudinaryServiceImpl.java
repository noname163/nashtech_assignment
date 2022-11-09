package com.nash.assignment.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

@Service
public class CloudinaryServiceImpl {

    @Autowired
    Cloudinary cloudinary;

    public String uploadImage(MultipartFile multipartFile) throws IOException {
        if(multipartFile==null){
            return null;
        }
        Map cloudinaryApi = cloudinary.uploader().upload(multipartFile.getBytes(), ObjectUtils.asMap("resource_type", "auto"));
        String url = (String) cloudinaryApi.get("secure_url");
        return url;

    }

    public List<String> uploadImages(MultipartFile[] multipartFile) throws IOException {
        if(multipartFile==null){
            return Collections.emptyList();
        }
        List<String> urls = new ArrayList<>();
        for (MultipartFile image : multipartFile) {
            Map cloudinaryApi = cloudinary.uploader().upload(image.getBytes(), ObjectUtils.asMap("resource_type", "auto"));
            urls.add((String) cloudinaryApi.get("secure_url"));
        }
        return urls;

    }
}
