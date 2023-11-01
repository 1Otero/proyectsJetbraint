package com.example.testjwt.security;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@AllArgsConstructor
public class WebSecurityConfig {

    //Esto es lo ultimo despues de el paso de memoria, cuando se asigna esto ya no deberiamos usar los usuarios en memoria
    private final UserDetailsService userDetailService;
    private final JWTAuthorizationFilter jwtAuthorizationFilter;

    //Configurar tipo de aceso a rutas y forma de aceder a los recursos, tambien para poder probar con postman
    //Este AuthenticationManager solo funciona con jwt-api
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, AuthenticationManager auth){

        //Primero se va tener en cuenta esta condicion.... verifica usuario y contraseña
        JWTAuthenticationFilter jwtAuthenticationFilter1= new JWTAuthenticationFilter();
        jwtAuthenticationFilter1.setAuthenticationManager(auth);
        jwtAuthenticationFilter1.setFilterProcessesUrl("/login");

        try {
            return http
                    .csrf().disable()
                    //.formLogin().permitAll().and().
                    .authorizeHttpRequests(e -> {
                e.requestMatchers("/user/**").permitAll()
                  .requestMatchers("/admin/**").hasRole("admin")
                  .requestMatchers("/ini/**").hasRole("cli")
                  .anyRequest().authenticated();
            })
                    .httpBasic()
                    .and()
                    .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    .and()
                    //Primero verifica con loginform
                    .addFilter(jwtAuthenticationFilter1)
                    //despues con class authorization para leer los token que ya tienen authorizado por username y password
                    .addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class)
                    //.addFilterAfter(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class)
                    .build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //Se crean un usuarios en memoria -> para sql ya se debe comentar
    /**@Bean
    public InMemoryUserDetailsManager userDetailsService(){
        UserDetails admin= User
                .withDefaultPasswordEncoder()
                .username("admin")
                .password("admin")
                .roles(new String[]{"admin"})
                .build();
        UserDetails user= User
                .withDefaultPasswordEncoder()
                .username("cli")
                .password("cli")
                .roles(new String[]{"cli"})
                .build();
        return new InMemoryUserDetailsManager(admin, user);
    }*/

    //Implementas las configuraciones, clases y metodos que se encargaran de la authenticacion usarios
    @Bean
    AuthenticationManager authManager(HttpSecurity http){
        try {
            return http.getSharedObject(AuthenticationManagerBuilder.class)
                    .userDetailsService(userDetailService)
                    .passwordEncoder(passwordEncoder())
                    .and()
                    .build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //Esto retorna para desencriptar -> se debe buscar mas
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
