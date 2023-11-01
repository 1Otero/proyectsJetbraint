package com.example.ventas.utils.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
@Configuration
@AllArgsConstructor
public class WebSecurityConfig{
  private UserDetailsService userDetailsService;
  public SecurityFilterChain filterChain(HttpSecurity http){
      try {
        return http.csrf().disable().authorizeHttpRequests((e)->{
            e.requestMatchers("/ini/**").hasRole("CLI")
                    .requestMatchers("/admin/**").hasRole("ADMIN");
        }).httpBasic()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .build();
      }catch(Exception e){
          System.out.println("Error webSecurity - SecurityFilterChain");
          e.fillInStackTrace();
          return null;
      }
  }
  /*@Bean
  public InMemoryUserDetailsManager getAuthManager(){
      UserDetails user1= User.withDefaultPasswordEncoder()
              .username("van")
              .password("root")
              .roles("CLI").roles("ADMIN")
              .build();
      UserDetails user2= User.withDefaultPasswordEncoder()
              .username("tero")
              .password("cli")
              .roles("CLI")
              .build();
      return new InMemoryUserDetailsManager(user1, user2);
  }*/
  @Bean
  AuthenticationManager getAuthManager(HttpSecurity http){
      try{
        return http.
        getSharedObject(AuthenticationManagerBuilder.class)
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
  public PasswordEncoder passwordEncoder(){
      return new BCryptPasswordEncoder();
  }
}

