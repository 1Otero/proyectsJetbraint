package com.example.testsecurity.security;

import com.example.testsecurity.entitys.AuthCredentials;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter{

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException{
      AuthCredentials dataUser= new AuthCredentials();
      try{
         dataUser= new ObjectMapper().readValue(request.getReader(), AuthCredentials.class);
      }catch(Exception e){
            System.out.println("Error leyendo datos de signup ");
            System.out.println(e);
      }
      UsernamePasswordAuthenticationToken usernamePAT= new UsernamePasswordAuthenticationToken(dataUser.getMail(),dataUser.getPassword());
      //Recordar que aqui pasa a service para realizar la consulta a BD
      return getAuthenticationManager().authenticate(usernamePAT);
    }
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication auth){
     try {
         AuthCredentials dataUser= (AuthCredentials) auth.getPrincipal();
         String newToken= TokenUtils.crearToken(dataUser.getUserName(), dataUser.getMail());
         response.setHeader("Authorization", "Bearer " + newToken);
         response.getWriter().flush();
         super.successfulAuthentication(request, response, chain, auth);
     }catch(IOException e){
         System.out.println("Error successfulAuthentication IO ");
         System.out.println(e);
     }catch(ServletException e){
         System.out.println("Error successfulAutentication Servlet ");
         System.out.println(e);
     }
    }
}
