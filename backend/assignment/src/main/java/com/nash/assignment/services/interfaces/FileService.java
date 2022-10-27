package com.nash.assignment.services.interfaces;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {

    boolean saveFile(String uploadDir, String fileName,
            MultipartFile multipartFile);
}
