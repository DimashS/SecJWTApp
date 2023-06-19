package com.dimash.securityApp.controller;

import com.dimash.securityApp.dto.AuthenticationDTO;
import com.dimash.securityApp.models.Person;
import com.dimash.securityApp.security.JWTUtil;
import com.dimash.securityApp.service.RegistrationService;
import com.dimash.securityApp.util.PersonValidator;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final RegistrationService registrationService;
    private final PersonValidator personValidator;
    private final JWTUtil jwtUtil;
    private final ModelMapper modelMapper;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthController(RegistrationService registrationService, PersonValidator personValidator, JWTUtil jwtUtil, ModelMapper modelMapper, AuthenticationManager authenticationManager) {
        this.registrationService = registrationService;
        this.personValidator = personValidator;
        this.jwtUtil = jwtUtil;
        this.modelMapper = modelMapper;
        this.authenticationManager = authenticationManager;
    }


    @PostMapping("/login")
    public Map<String, String> performLogin(@RequestBody AuthenticationDTO authenticationDTO) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
                = new UsernamePasswordAuthenticationToken(authenticationDTO.getUsername(),authenticationDTO.getPassword());
        // вместо нас проверит, на его основе позволит проверять
        try {
            authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        } catch (BadCredentialsException e) { // если неправильные логин и пароль не из бд
            return Map.of("message","Incorrect credentials");
        }
        String token = jwtUtil.generateToken(authenticationDTO.getUsername());
        return Map.of("jwtToken",token);
    }
    @PostMapping("/registration")
    // @RequestBody так как будем отправлять подзаброс
    // с помощью jackson конвертирует в JSON нашу ошибку, но надо бы @ExceptionHandler
    public Map<String, String> performRegistration(@RequestBody @Valid com.dimash.spring.restApp.dto.PersonDTO personDTO, BindingResult bindingResult) {
        // valid => for validation through html
        Person person = convertToPerson(personDTO); // используем DTO как прослойку и конвертируем в Person наш PersonDTO
        personValidator.validate(person, bindingResult); // валидация, то есть есть ли наша персона в базе данных (имя)
        if (bindingResult.hasErrors()) {
            return Map.of("message", "Error"); // если есть ошибка отправляем Error
        }
        registrationService.register(person); // если все ок и валидация прошла, то регистрируем пользователя
        String token = jwtUtil.generateToken(person.getUsername()); // строка токена которая сгенерится из токена
        return Map.of("jwt-token", token); // вернем jwt token
    }

    public Person convertToPerson(com.dimash.spring.restApp.dto.PersonDTO personDTO) {
        return this.modelMapper.map(personDTO, Person.class);
    }
}