package com.nash.assignment.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nash.assignment.modal.Account;

public class ImageAccountDto {

    private String url;

    private Account avatar;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @JsonIgnore
    public Account getAvatar() {
        return avatar;
    }

    public void setAvatar(Account avatar) {
        this.avatar = avatar;
    }
}
