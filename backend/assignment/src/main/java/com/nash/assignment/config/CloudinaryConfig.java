package com.nash.assignment.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

@Configuration
public class CloudinaryConfig {


    private Environment environment;
    
    public CloudinaryConfig(Environment environment) {
        this.environment = environment;
    }


    @Bean
    public Cloudinary cloudinary(){
        String cloudName = environment.getProperty("cloudinary.cloud.name");
        String apiKey = environment.getProperty("cloudinary.cloud.key");
        String apiSecret = environment.getProperty("cloudinary.api.secrets");
        Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
            "cloud_name", cloudName,
            "api_key", apiKey,
            "api_secret", apiSecret));
            return cloudinary;
    }
}
