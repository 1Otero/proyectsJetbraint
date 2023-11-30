package com.example.jwtspring.security;

import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
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
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
@AllArgsConstructor
public class SecurityConfig {
    private UserDetailsService userDetailsService;
    private final JWTAuthorizationFilter jwtAuthorizationFilter;
    @Bean
    //public SecurityFilterChain securityFilterChain(HttpSecurity http){
    public SecurityFilterChain securityFilterChain(HttpSecurity http, AuthenticationManager auth){
        JWTAuthenticationFilter jwtAuthenticationFilter= new JWTAuthenticationFilter();
        jwtAuthenticationFilter.setAuthenticationManager(auth);
        jwtAuthenticationFilter.setFilterProcessesUrl("/login");
        try {
            http
                    .csrf().and()
                    /*.authorizeHttpRequests((e) -> e
                            //.anyRequest().authenticated()).httpBasic(Customizer.withDefaults());
                            .requestMatchers("/api/admin/**").hasRole("admin")
                            .requestMatchers("/api/user/**").hasRole("user"))
                                //.formLogin().and().logout();
                                .httpBasic(Customizer.withDefaults());
                            //authenticated()).httpBasic(Customizer.withDefaults());*/
                    /*.authorizeHttpRequests((e) -> e
                            .requestMatchers("/api/admin/").hasAnyRole("admin","user"))
                    .httpBasic(Customizer.withDefaults());*/
                    //.addFilterAfter(new JWTAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
                    .authorizeHttpRequests((e) -> {
                        e.requestMatchers("/api/admin/**").hasRole("ADMIN")
                         .requestMatchers("/api/user/**").hasRole("USER")
                         .requestMatchers("/api/ini/**").permitAll();
                    })
            ///Inicia aqui
                    .httpBasic()
                    .and()
                    .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    .and()
                    .addFilter(jwtAuthenticationFilter)
                    .addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class)
                    ////Hasta aqui
            ;//.formLogin().loginPage("/api/ini/login");

            return http.build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
/*
    @Bean
    public InMemoryUserDetailsManager userDetailsService(){
        UserDetails u= User.withDefaultPasswordEncoder()
                .username("roro")
                .password("1234")
                .roles("admin","user")
                .build();
        return new InMemoryUserDetailsManager(u);
    }
*/

    /*
    @Bean
    public UserDetailsService userDetailsServicee(){
        UserDetails userd= User
                .withDefaultPasswordEncoder()
                .username("roro")
                .password("1234")
                .roles("admin")
                .authorities("admin")
                .build();
        return new InMemoryUserDetailsManager(userd);
    }*/
    @Bean
    AuthenticationManager getAuthManager(HttpSecurity http){
        try{
            return http.getSharedObject(AuthenticationManagerBuilder.class)
                    .userDetailsService(userDetailsService)
                    .passwordEncoder(passwordEncoder())
                    .and()
                    .build();
        }catch(Exception e){
            System.out.println("Error web security - getAuthManager");
            e.fillInStackTrace();
            return null;
        }
    }
    @Bean
    PasswordEncoder passwordEncoder(){return new BCryptPasswordEncoder();}
}
