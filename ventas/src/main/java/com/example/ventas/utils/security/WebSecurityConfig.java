package com.example.ventas.utils.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@AllArgsConstructor
public class WebSecurityConfig{
    private UserDetailsService userDetailsService;
    private final JWTAuthorizationFilter jwtAuthorizationFilter;
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, AuthenticationManager auth){
        JWTAuthenticationFilter jwtAuthenticationFilter= new JWTAuthenticationFilter();
        jwtAuthenticationFilter.setAuthenticationManager(auth);
        jwtAuthenticationFilter.setFilterProcessesUrl("/login");
        //jwtAuthenticationFilter.setAuthenticationSuccessHandler(new SimpleUrlAuthenticationSuccessHandler("/admin/"));
        jwtAuthenticationFilter.setAuthenticationFailureHandler(new SimpleUrlAuthenticationFailureHandler("/login"));
        try {
            //return http.csrf((e) -> e.ignoringRequestMatchers("/ini/**"))
            return http
                    //.csrf(csrf -> csrf.disable())
                    .csrf().disable()
                    .authorizeHttpRequests((e)->{
                        //e.requestMatchers("/ini/**").hasRole("CLI")
                        e.requestMatchers("/ini/**").permitAll()
                                .requestMatchers("/css/index.css").permitAll()
                                .requestMatchers("/auth/**").permitAll()
                                .requestMatchers("/admin/**").hasRole("ADMIN")
                                .anyRequest().authenticated();
           /*e.requestMatchers(HttpMethod.GET,"/ini/**").permitAll()
             .requestMatchers("/admin/**").hasRole("ADMIN")*/
             //.anyRequest().authenticated();
                    })
                    .httpBasic()
                    .and()
                    .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    .and()
                    .addFilter(jwtAuthenticationFilter)
                    .addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class)
                    .logout(l -> l.logoutUrl("/logout").clearAuthentication(true).deleteCookies("JSESSIONID").invalidateHttpSession(true))
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

