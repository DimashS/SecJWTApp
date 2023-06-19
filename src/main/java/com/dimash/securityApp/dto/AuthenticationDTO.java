package com.dimash.securityApp.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class AuthenticationDTO {
    // данные для аутентификации
    @NotEmpty(message = "Name shouldn't be empty")
    @Size(min = 2, max = 30, message = "name could be only between 2 or 30 characters")
    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
