package com.example.testsecurity.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JWTAuthorizationFilter extends OncePerRequestFilter {

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain){
      String bearerToken= request.getHeader("Authorization");
      try{
          if(bearerToken != null && bearerToken.startsWith("Bearer ")){
             String token= bearerToken.replace("Bearer ", "");
             UsernamePasswordAuthenticationToken usernamePAT= TokenUtils.getAuthentication(token);
             SecurityContextHolder.getContext().setAuthentication(usernamePAT);
          }
          chain.doFilter(request, response);
      }catch(Exception e){
          System.out.println("Error authentication con token ");
      }
    }
}
