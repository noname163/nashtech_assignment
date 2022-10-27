package com.nash.assignment.services;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileServiceImpl {

    private final String preDir = "backend/assignment/src/main/resources/static/images/";

    public ResponseEntity<String> saveFile(String uploadDir, String fileName,
            MultipartFile multipartFile) throws IOException {

        if (multipartFile == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        String extension = getExtentionName(multipartFile);
        String result = fileName + "." + extension;
        String directory = preDir + uploadDir;
        Path uploadPath = Paths.get(directory);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        InputStream inputStream = multipartFile.getInputStream();
        Path filePath = uploadPath.resolve(result);
        Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    public ResponseEntity<List<String>> saveMultipleFile(String uploadDir,
            MultipartFile[] multipartFile) throws IOException {

        List<String> result = new ArrayList<>();
        if (multipartFile == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        int count = 0;
        String name = "";
        for (MultipartFile image : multipartFile) {

            String extension = getExtentionName(image);
            name = ++count + "." + extension;
            result.add(name);

            String directory = preDir + uploadDir;
            Path uploadPath = Paths.get(directory);

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            InputStream inputStream = image.getInputStream();
            Path filePath = uploadPath.resolve(name);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    private String getExtentionName(MultipartFile file) {
        String filename = file.getOriginalFilename();
        int index = filename.indexOf('.');
        String extension = filename.substring(index + 1, filename.length()).toLowerCase();
        return extension;
    }
}
