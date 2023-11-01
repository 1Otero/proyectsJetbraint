package com.example.jwtspring.controllers;


import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.SessionScope;
import io.jsonwebtoken.Jwts;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
//@PreAuthorize("permitAll()")
@EnableWebSecurity
@RequestMapping(value = "/api/ini")
//@CrossOrigin(origins = {"http://localhost:8080","https://localhost:80"}, methods = {RequestMethod.GET}, maxAge = 12L)
public class InicioController {

    private String secrectKey= "secretKey";
    public InicioController(){
        //AManager= a;
    }

    //@Secured({"IS_AUTHENTICATED_ANONYMOUSLY","ROLE_ANONYMOUS"})
    @GetMapping(name = "Rutainicio", value = "/")
    public String Saludo(){
        return "Bienvenido sr User! ";
    }

    @GetMapping(value = "/login")
    public String login(){
        System.out.println("Iniciando session!");
        String user= "roro";
        //String token= this.getJWTToken(user);
        String token= "233121";
        System.out.println("User: ".concat(user).concat(" - Token ").concat(token));
        token= this.d();
        return "Iniciando session - Token: ".concat(token);
    }
    public String getJWTToken(String usr){
        String Token= "null por default";
        Set<GrantedAuthority> grand= new HashSet<>();
        grand.add(new SimpleGrantedAuthority("admin"));
        grand.add(new SimpleGrantedAuthority("user"));
        Token= Jwts
                .builder()
                .setId("fff")
                .setSubject(usr)
                .claim("authorities",
                        grand.stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.toList()))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 600000))
                .signWith(SignatureAlgorithm.HS512,
                        secrectKey.getBytes()).compact();
            String TokenU= "Bearer " + "Token: ".concat(Token);
        System.out.println("Token: ".concat(TokenU));
        //this.d();
        return "Bearer " + Token;
    }
    public String d(){
        Set<GrantedAuthority> sGrantedAuthority= new HashSet<>();
        sGrantedAuthority.add(new SimpleGrantedAuthority("admin"));
        sGrantedAuthority.add(new SimpleGrantedAuthority("user"));

        UserDetails u = new User("roro","1234",true,true,true,true, sGrantedAuthority);
        //Authentication authentication=
        /*System.out.println("datos pasados: " + u.getUsername() + " - " + u.getPassword());
        UsernamePasswordAuthenticationToken authentication=
                new UsernamePasswordAuthenticationToken(u.getUsername(),u.getPassword());
        UserDetails us= (UserDetails) authentication.getPrincipal();
        SecurityContextHolder.getContext().setAuthentication(authentication);*/

        String ud= Jwts.builder()
                .setSubject("refff")
                .claim("name","iva")
                .claim("sccope","admin")
                .signWith(SignatureAlgorithm.ES256,secrectKey)
                .compact();
        return ud;
    }
}
