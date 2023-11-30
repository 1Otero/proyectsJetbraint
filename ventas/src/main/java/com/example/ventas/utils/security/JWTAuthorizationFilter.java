package com.example.ventas.utils.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.Base64;
import java.util.Iterator;

@Component
public class JWTAuthorizationFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain){
      String bearerToken= request.getHeader("Authorization");
      String bearerCookie= "";
      if(request.getCookies() != null){
        for(Cookie cookie: request.getCookies()){
          if(cookie.getName().equals("Authorization")){
              bearerCookie= cookie.getValue();
              System.out.println("Authorization of cookie");
              System.out.println(bearerCookie);
              try{
               byte[] decodeBytes= Base64.getDecoder().decode(bearerCookie);
               bearerCookie= new String(decodeBytes);
               System.out.println("decodeTokem# ");
               System.out.println(bearerCookie);
              }catch(Exception e) {
                  System.out.println("Decode token cookie authorization o es null");
                  System.out.println(e);
              }
          }
        }
      }
      try{
          if (!bearerCookie.equals("") && bearerCookie.startsWith("Bearer ")) {
             bearerToken= bearerCookie;
             String token = bearerCookie.replace("Bearer ", "");
             System.out.println("TOKEN: ");
             System.out.println(token);
             UsernamePasswordAuthenticationToken usernamePAT= TokenUtils.getAuthentication(token);
             SecurityContextHolder.getContext().setAuthentication(usernamePAT);
          }else{
           if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
               String token = bearerToken.replace("Bearer ", "");
               UsernamePasswordAuthenticationToken usernamePAT = TokenUtils.getAuthentication(token);
               System.out.println(usernamePAT.getPrincipal());
               SecurityContextHolder.getContext().setAuthentication(usernamePAT);
           }
         }
          System.out.println("Token ingreso? ");
          System.out.println(bearerToken);
          chain.doFilter(request, response);
      }catch(Exception e){
        System.out.println("JWTAuthorizationFilter - doFilterInternal");
        e.fillInStackTrace();
      }
    }
    public boolean requiereAutenticacion(HttpServletRequest request){
          String NewURL=request.getRequestURL().toString();
          String[] WhiteURL= {"/ini","/public","/normal","http://localhost:8080/ini/"};
          //System.out.println(Arrays.asList(WhiteURL).contains(NewURL));
          //System.out.println(Arrays.stream(WhiteURL).anyMatch(e -> e.startsWith(NewURL)));
          if(Arrays.asList(WhiteURL).contains(NewURL)){
              return false;
          }
          return true;
    }
}
