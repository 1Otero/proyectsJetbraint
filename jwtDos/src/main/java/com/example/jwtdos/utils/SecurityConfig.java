package com.example.jwtdos.utils;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ldap.core.support.BaseLdapPathContextSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DefaultAuthenticationEventPublisher;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.userdetails.DaoAuthenticationConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.ldap.EmbeddedLdapServerContextSourceFactoryBean;
import org.springframework.security.config.ldap.LdapBindAuthenticationManagerFactory;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

   @Bean
   public SecurityFilterChain securityFilterChain(HttpSecurity http){
       try {
       http
               /**/.authorizeHttpRequests((e) -> {
                  e
                   .requestMatchers("/api/ini/**").permitAll()
                          .requestMatchers("/api/admin/**").hasRole("admin");

               }).formLogin().defaultSuccessUrl("/api/admin/", true)
               .failureUrl("/");
               //.formLogin().defaultSuccessUrl("/api/admin/", true)
           return http.build();
       } catch (Exception e) {
           throw new RuntimeException(e);
       }
   }

   @Bean
   public InMemoryUserDetailsManager userDetailsService(){
       UserDetails admin = User.withDefaultPasswordEncoder()
               .username("iva")
               .password("1234")
               .roles("admin")
               .build();
       UserDetails cli= User.withDefaultPasswordEncoder()
               .username("roro")
               .password("1234")
               .roles("cli")
               .build();
        return new InMemoryUserDetailsManager(admin,cli);
   }

   /*@Bean
   public void userDetailsService(AuthenticationManagerBuilder auth){
       try {
           auth.userDetailsService(userDetailsService());
       } catch (Exception e) {
           throw new RuntimeException(e);
       }
   }*/
   /*@Bean
   DefaultAuthenticationEventPublisher defaultAuthenticationEventPublisher(ApplicationEventPublisher delegate) {
       return new DefaultAuthenticationEventPublisher();
   }*/
   @Bean
   public EmbeddedLdapServerContextSourceFactoryBean contextSourceFactoryBean() {
       EmbeddedLdapServerContextSourceFactoryBean contextSourceFactoryBean =
               EmbeddedLdapServerContextSourceFactoryBean.fromEmbeddedLdapServer();
       contextSourceFactoryBean.setPort(0);
       return contextSourceFactoryBean;
   }

    @Bean
    AuthenticationManager ldapAuthenticationManager(
            BaseLdapPathContextSource contextSource) {
        LdapBindAuthenticationManagerFactory factory =
                new LdapBindAuthenticationManagerFactory(contextSource);
        factory.setUserDnPatterns("uid={0},ou=people");
        //factory.setUserDetailsContextMapper(new PersonContextMapper());
        return factory.createAuthenticationManager();
    }
}
