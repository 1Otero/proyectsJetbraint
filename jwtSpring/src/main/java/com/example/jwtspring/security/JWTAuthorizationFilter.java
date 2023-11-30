package com.example.jwtspring.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.util.Arrays;

@Component
public class JWTAuthorizationFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain){
      String bearerToken= request.getHeader("Authorization");
      try{
         if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
               System.out.println("Bearer? ");
               System.out.println(bearerToken.startsWith("Bearer "));
               String token = bearerToken.replace("Bearer ", "");
               UsernamePasswordAuthenticationToken usernamePAT = TokenUtils.getAuthentication(token);
               SecurityContextHolder.getContext().setAuthentication(usernamePAT);
         }
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
