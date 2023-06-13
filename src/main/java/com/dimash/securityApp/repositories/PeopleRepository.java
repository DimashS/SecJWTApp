package com.dimash.securityApp.repositories;

import com.dimash.securityApp.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
// для создания Spring Boot метода который будет шаблоном для вызова метода, достающего человека из бд
public interface PeopleRepository extends JpaRepository<Person, Integer> {
    Optional<Person> findByUsername(String username);
}
