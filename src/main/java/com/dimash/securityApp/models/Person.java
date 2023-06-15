package com.dimash.securityApp.models;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "Person")
public class Person {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotEmpty(message = "Name cam't be empty")
    @Column(name = "username")
    private String username;
    @Min(value = 1900,message = "Can't be")
    @Max(value = 2023, message = "Impossible")
    @Column(name = "year_of_birth")
    private int year_of_birth;
    @Column(name = "password")
    private String password;

    @Column(name = "role")
    private String role;
    public Person() {
    }

    public Person(String username, int year_of_birth) {
        this.username = username;
        this.year_of_birth = year_of_birth;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", year_of_birth=" + year_of_birth +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
