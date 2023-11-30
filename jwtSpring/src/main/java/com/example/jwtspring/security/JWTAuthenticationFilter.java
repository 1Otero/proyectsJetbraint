package com.example.jwtspring.security;


import com.example.jwtspring.modelos.AuthUser;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter{
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response){
      AuthUser authUser= new AuthUser();
      try{
        System.out.println(request.getParameter("username"));
        System.out.println(request.getParameter("Password"));
        System.out.println(new ObjectMapper().readValue(request.getInputStream(), AuthUser.class));
        authUser= new ObjectMapper().readValue(request.getReader(), AuthUser.class);
        System.out.println(authUser);
      }catch(IOException e){
          System.out.println("Error JWTAuthenticationFilter - attemptAuthentication");
          System.out.println(e);
          e.fillInStackTrace();
      }
      UsernamePasswordAuthenticationToken usernamePAT= new UsernamePasswordAuthenticationToken(authUser.getEmail(), authUser.getPass());
      return getAuthenticationManager().authenticate(usernamePAT);
    }
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication auth){
      try{
        AuthUser useBody= (AuthUser) auth.getPrincipal();
        String newToken= TokenUtils.crearToken(useBody.getUsername(), useBody.getEmail());
        System.out.println("new token:".concat(newToken));
        response.setHeader("Authorization", "Bearer ".concat(newToken));
        response.getWriter().flush();
        super.successfulAuthentication(request, response, chain, auth);
      }catch(Exception e){
        System.out.println("Error JWTAuthenticationFilter - successfulAuthentication");
        e.fillInStackTrace();
      }

    }
}
