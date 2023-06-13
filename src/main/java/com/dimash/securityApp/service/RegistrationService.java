package com.dimash.securityApp.service;

import com.dimash.securityApp.models.Person;
import com.dimash.securityApp.repositories.PeopleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class RegistrationService {
    private final PeopleRepository peopleRepository;
    private final PasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public RegistrationService(PeopleRepository peopleRepository, PasswordEncoder bCryptPasswordEncoder) {
        this.peopleRepository = peopleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Transactional
    public void register(Person person) {
        // тут устанавливается пароль при регистрации и мы шифруем его
        person.setPassword(bCryptPasswordEncoder.encode(person.getPassword()));
        person.setRole("ROLE_USER");
        // затем сохраним его
        peopleRepository.save(person);
    }
}
