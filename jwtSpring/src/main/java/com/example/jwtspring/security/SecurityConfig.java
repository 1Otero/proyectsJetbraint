package com.example.jwtspring.security;

import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http){
        try {
            http
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
                        e.requestMatchers("/api/admin/**").hasRole("admin")
                         .requestMatchers("/api/user/**").hasRole("user")
                         .requestMatchers("/api/ini/**").permitAll();
                    })
            ;//.formLogin().loginPage("/api/ini/login");

            return http.build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Bean
    public InMemoryUserDetailsManager userDetailsService(){
        UserDetails u= User.withDefaultPasswordEncoder()
                .username("roro")
                .password("1234")
                .roles("admin","user")
                .build();
        return new InMemoryUserDetailsManager(u);
    }

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
}
