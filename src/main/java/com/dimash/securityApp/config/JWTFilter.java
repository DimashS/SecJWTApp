package com.dimash.securityApp.config;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.dimash.securityApp.security.JWTUtil;
import com.dimash.securityApp.service.PersonDetailsService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JWTFilter extends OncePerRequestFilter {
    private final JWTUtil jwtUtil;
    private final PersonDetailsService personDetailsService;

    public JWTFilter(JWTUtil jwtUtil, PersonDetailsService personDetailsService) {
        this.jwtUtil = jwtUtil;
        this.personDetailsService = personDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization"); // из запроса извлечем header
        // JWT token принято передавать в Header под именем Authorization
        if (authHeader != null && !authHeader.isBlank() && authHeader.startsWith("Bearer ")) { // в запросе есть токен
            String jwt = authHeader.substring(7);
            if (jwt.isBlank()) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid JWT token in Bearer Header");
                // если ничего нет то все запросы будут с ошибкой
            } else {
                try {
                    String username = jwtUtil.validateTokenAndRetrieveClaim(jwt); // если получили JWT токен без исключений
                    // то jwt реально был подписан нами и мы нашли наш username => выдали мы
                    UserDetails userDetails = personDetailsService.loadUserByUsername(username);
                    // просто проверка уже через БД

                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                            new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                    //

                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                } catch (JWTVerificationException e) {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid JWT token");
                }
            }
        }
        filterChain.doFilter(request,response); // передаем дальше по цепочке перехваченный фильтром запрос
    }
}
