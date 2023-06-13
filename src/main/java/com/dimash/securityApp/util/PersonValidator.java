package com.dimash.securityApp.util;

import com.dimash.securityApp.models.Person;
import com.dimash.securityApp.service.PersonDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class PersonValidator implements Validator {
    private final PersonDetailsService personDetailsService;

    @Autowired
    public PersonValidator(PersonDetailsService personDetailsService) {
        this.personDetailsService = personDetailsService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz); // validation for whom?
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;
        try {
            personDetailsService.loadUserByUsername(person.getUsername());
        } catch (UsernameNotFoundException ignore) {
            // лучше реализовать другой сервис, где мы будем доставать человека и не бросать исключение
            return;
        }
        // если ошибки которая будет означать правильность валидации и пройденную проверку, не будет выловлена
        // значит это плохо
        errors.rejectValue("username","","This user exists ");
    }
}
