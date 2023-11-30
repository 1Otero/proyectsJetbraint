package com.example.ventas.utils.security;

import com.example.ventas.modelos.AuthUser;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter{
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response){
      AuthUser authUser= new AuthUser();
      try{
        authUser= new ObjectMapper().readValue(request.getReader(), AuthUser.class);
      }catch(Exception e){
          System.out.println("Error JWTAuthenticationFilter - attemptAuthentication");
          e.fillInStackTrace();
      }
      UsernamePasswordAuthenticationToken usernamePAT= new UsernamePasswordAuthenticationToken(authUser.getEmail(), authUser.getPass());
      return getAuthenticationManager().authenticate(usernamePAT);
    }
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication auth){
      try{
        UserDetailsImpl useBody= (UserDetailsImpl) auth.getPrincipal();
        System.out.println("user sucess");
        System.out.println(useBody.getUsername());
        System.out.println(useBody.getPassword());
        String newToken= TokenUtils.crearToken(useBody.getUsername(), useBody.getPassword());
        System.out.println("new token:".concat(newToken));
        //response.setHeader("Authorization", "Bearer ".concat(newToken));
        response.setHeader("Authorization", "Bearer " + newToken);
        //Cookie c= new Cookie("Authorization", URLEncoder.encode("Bearer " + newToken.getBytes(), "UTF-8"));
        Cookie c= new Cookie("Authorization", Base64.getEncoder().encodeToString("Bearer ".concat(newToken).getBytes()));
        c.setHttpOnly(true);
        response.addCookie(c);
        response.getWriter().flush();
        if(request.getCookies() != null) {
            for (Cookie co : request.getCookies()) {
                System.out.println("Cookie: ____");
                System.out.println(co.getValue());
            }
        }
        super.successfulAuthentication(request, response, chain, auth);
      }catch(Exception e){
        System.out.println("Error JWTAuthenticationFilter - successfulAuthentication");
        System.out.println(e);
        e.fillInStackTrace();
      }

    }
}
