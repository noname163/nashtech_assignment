package com.nash.assignment.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.nash.assignment.constant.StatusEnum;
import com.nash.assignment.modal.Image;
import com.nash.assignment.modal.Role;

import lombok.Data;

@Data
public class AccountDto {

    private long id;
    @NotBlank(message = "Phone Number Cannot Empty")
    @Pattern(regexp = "^(0|84)(2(0[3-9]|1[0-6|8|9]|2[0-2|5-9]|3[2-9]|4[0-9]|5[1|2|4-9]|6[0-3|9]|7[0-7]|8[0-9]|9[0-4|6|7|9])|3[2-9]|5[5|6|8|9]|7[0|6-9]|8[0-6|8|9]|9[0-4|6-9])([0-9]{7})$",message = "Phone Need In Format 10 Digit Number")
    @JsonProperty("phoneNumber")
    private String phoneNumber;
    @NotBlank(message = "Email Cannot Empty")
    @Pattern(regexp = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+\\.[Aa-zA-Z0]{2,6}$", message = "Email Must Match example@example.com'")
    private String email;
    @NotBlank(message = "Full Name Cannot Empty")
    @JsonProperty("fullName")
    private String fullName;
    @NotBlank(message = "Password Cannot Empty")
    @JsonProperty("password")
    private String password;

    private Image image;

    private String test;

    private Role role;

    private StatusEnum status;

    public String getTest() {
        return test;
    }

    public void setTest(String test) {
        this.test = test;
    }

    public StatusEnum getStatus() {
        return status;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {

        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

}
