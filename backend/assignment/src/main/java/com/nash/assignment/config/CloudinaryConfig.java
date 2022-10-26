package com.nash.assignment.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cloudinary.*;
import com.cloudinary.utils.ObjectUtils;

@Configuration
public class CloudinaryConfig {

    @Bean
    public Cloudinary cloudinary(){
        Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
            "cloud_name", "dyvrvbcxx",
            "api_key", "212142713724382",
            "api_secret", "NA_z6IN7hUbfIWIdUjRCANtwjgI"));
            return cloudinary;
    }
}
