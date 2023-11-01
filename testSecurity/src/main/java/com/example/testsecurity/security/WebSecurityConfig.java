package com.example.testsecurity.security;

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
    private final UserDetailsService userDetailsService;
    //Debe ser una interfaz, se va encargar de llamar el metodo donde se consultara en BD como con UserDetailsService
    //private UserDetailsServiceMe userDetailsService
    private final JWTAuthorizationFilter jwtAuthorizationFilter;
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, AuthenticationManager auth){
        JWTAuthenticationFilter jwtAuthenticationFilter= new JWTAuthenticationFilter();
        jwtAuthenticationFilter.setAuthenticationManager(auth);
        jwtAuthenticationFilter.setFilterProcessesUrl("/login");
        try{
              return http
                      .csrf().disable()
                      .authorizeHttpRequests(e -> {
                          e.requestMatchers("/ini/**").permitAll()
                                  .requestMatchers("/admin/**").hasRole("admin")
                                  .anyRequest().authenticated();
                      })
                      .httpBasic()
                      .and()
                      .sessionManagement()
                      .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                      .and()
                      .addFilter(jwtAuthenticationFilter)
                      .addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class)
                      .build();
          }catch(Exception e){
              System.out.println(e);
              System.out.println("Error filter");
              return null;
          }
    }
    //comentar despues
    /*@Bean
    public InMemoryUserDetailsManager userDetailsManager(){
        UserDetails user= User.withDefaultPasswordEncoder()
                .username("root")
                .password("root")
                .roles("admin")
                .build();
        UserDetails user1= User.withDefaultPasswordEncoder()
                .username("user")
                .password("user")
                .roles("cli")
                .build();
        return new InMemoryUserDetailsManager(user, user1);
    }*/
    @Bean
    AuthenticationManager getAuthManager(HttpSecurity http){
       try{
           return http
                   .getSharedObject(AuthenticationManagerBuilder.class)
                   .userDetailsService(userDetailsService)
                   .passwordEncoder(passwordEncoder())
                   .and()
                   .build();
       }catch(Exception e){
           System.out.println("Error en la configuracion userDetailService y passwordEncoder con bean");
           return null;
       }
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
