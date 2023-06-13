package com.dimash.securityApp.config;


import com.dimash.securityApp.service.PersonDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final PersonDetailsService personDetailsService;
    @Autowired
    public SecurityConfig(PersonDetailsService personDetailsService) {
        this.personDetailsService = personDetailsService;
    }

    @Bean
    public PasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // конфигурация самого спринг секьюрити
        // конфигурируем авторизацию
        http.authorizeRequests()
                .antMatchers("/admin").hasRole("ADMIN") // оказывается надо указать лишь ROLE, spring security
                //                                                                          все поймет кто это
                .antMatchers("/auth/login", "/auth/registration", "/error").permitAll()
                .anyRequest().hasAnyRole( "USER","ADMIN")
                .and().formLogin().loginPage("/auth/login") // передаем свою страницу
                .loginProcessingUrl("/login")
                // без неё не обработает запрос после регистрации и не перекинет
                // на страницу логина
                .defaultSuccessUrl("/hello", true)
                /*
                В данном случае, .defaultSuccessUrl("/hello", true) указывает, что пользователь всегда будет
                перенаправлен на URL-адрес "/hello" после успешного входа в систему,
                даже если у него был предварительный запрос на доступ к защищенному ресурсу.
                Параметр true гарантирует, что перенаправление будет выполняться всегда, игнорируя предыдущий запрос
                пользователя.
                 */
                .failureUrl("/auth/login?error") // знак вопроса => url куда будет переправлен клиент
                .and().logout().logoutUrl("/logout").logoutSuccessUrl("/auth/login");
//                .and().logout(); // сюда придут логин и пароль обработка данных
    }

    // настройка аутентификации HERE WE HAVE LOGIC OF AUTHENTICATION
    protected void configure(AuthenticationManagerBuilder builder) throws Exception {
        builder.userDetailsService(personDetailsService).passwordEncoder(bCryptPasswordEncoder());
    }

}
