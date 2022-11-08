package com.nash.assignment.controllers.admin;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nash.assignment.dto.request.CategoriesDto;
import com.nash.assignment.services.CategoriesServiceImpl;

@RestController
@RequestMapping("/admin/categories")
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
public class CategoryController {
    @Autowired
    CategoriesServiceImpl categoriesServiceImpl;

    @PostMapping()
    public ResponseEntity<CategoriesDto> insertCategory(@Valid @RequestBody CategoriesDto category) {
        CategoriesDto categoriesDto = categoriesServiceImpl.insertCategory(category);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                categoriesDto);
    }

    @PatchMapping(value = "/update-description")
    public ResponseEntity<CategoriesDto> updateCategoryDescription(@RequestBody CategoriesDto categoriesDto ){
        CategoriesDto result = categoriesServiceImpl.updateCategoryDescription(categoriesDto.getName(), categoriesDto.getDescription());
        return ResponseEntity.status(HttpStatus.OK).body(
            result
        );
    }
}
