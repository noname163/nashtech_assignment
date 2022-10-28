package com.nash.assignment.controllers;

import java.io.IOException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.nash.assignment.dto.AccountDto;
import com.nash.assignment.mapper.ImageMapper;
import com.nash.assignment.modal.Image;
import com.nash.assignment.services.AccountsServiceImpl;
import com.nash.assignment.services.CloudinaryServiceImpl;
import com.nash.assignment.services.FileServiceImpl;
import com.nash.assignment.services.ImageServiceImpl;

@RestController
@RequestMapping("/register")
public class RegisterController {

    @Autowired
    AccountsServiceImpl accountsServiceImpl;
    @Autowired
    FileServiceImpl fileServiceImpl;
    @Autowired
    ImageMapper imageMapper;
    @Autowired
    ImageServiceImpl imageServiceImpl;
    @Autowired
    CloudinaryServiceImpl cloudinaryServiceImpl;

    @PostMapping
    public ResponseEntity<AccountDto> insertAccount(@Valid @RequestBody AccountDto accountDto, MultipartFile avatar)
            throws IOException {
        String url = cloudinaryServiceImpl.uploadImage(avatar);
        Image image = imageServiceImpl.insertAvatar(url, accountDto);
        AccountDto account = accountsServiceImpl.insertAccounts(accountDto);
        account.setImage(image);
        account = accountsServiceImpl.updateAccountInformation(account);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                account);
    }

}
