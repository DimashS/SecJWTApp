package com.dimash.spring.restApp.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class PersonDTO {
    // те поля которые нас интересуют при общении клиента с сервером при запросах
    @NotEmpty(message = "Name shouldn't be empty")
    @Size(min = 2, max = 30, message = "username could be only between 2 or 30 characters")
    private String username;
    @Min(value = 1900, message = "Impossible")
    private int year_of_birth;

    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getYear_of_birth() {
        return year_of_birth;
    }

    public void setYear_of_birth(int year_of_birth) {
        this.year_of_birth = year_of_birth;
    }
}
