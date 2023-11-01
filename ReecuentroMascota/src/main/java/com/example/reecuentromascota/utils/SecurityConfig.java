package com.example.reecuentromascota.utils;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
//@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http){
        try {
            http
                    .authorizeHttpRequests(e -> {
                        e.requestMatchers("/api/ini/").permitAll()
                                .requestMatchers("/api/ini/test").permitAll()
                                .requestMatchers("/api/ini/testt").permitAll();
                    }).formLogin();
            return http.build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
