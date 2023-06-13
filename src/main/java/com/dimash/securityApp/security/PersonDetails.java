package com.dimash.securityApp.security;

import com.dimash.securityApp.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

// класс обертка над сущностью, чтобы напрямую не работать с Person => best practice
// impl UserDetails обязательно => стандарт
public class PersonDetails implements UserDetails {
    private final Person person; // человек из БД чтоб обернуть вокруг него PersonDetails

    @Autowired
    public PersonDetails(Person person) {
        this.person = person;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // или роль или действия
        return Collections.singletonList(new SimpleGrantedAuthority(person.getRole()));
    }

    @Override
    public String getPassword() {
        return this.person.getPassword();
    }

    @Override
    public String getUsername() {
        return this.person.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // пароль не просрочен
    }

    @Override
    public boolean isEnabled() { // включен ли
        return true;
    }
// данные аутентифицированного пользователя
    public Person getPerson() {
        return this.person;
    }
}
