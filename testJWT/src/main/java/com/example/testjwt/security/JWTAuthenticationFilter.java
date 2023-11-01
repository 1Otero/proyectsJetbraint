package com.example.testjwt.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.example.testjwt.entity.authCredentials;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    //Lee el usuario y verifica si existe o no
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response){
        authCredentials authCred= new authCredentials();
        String[] s= new String[]{"",""};
        try{
          authCred= new ObjectMapper().readValue(request.getReader(), authCredentials.class);
        }catch(Exception e){}
        List<GrantedAuthority> c = new ArrayList<>();
        c.add(new SimpleGrantedAuthority("admin"));
        c.add(new SimpleGrantedAuthority("cli"));
        UsernamePasswordAuthenticationToken usernamePAT= new UsernamePasswordAuthenticationToken(authCred.getEmail(), authCred.getPassword(), c);
        return getAuthenticationManager().authenticate(usernamePAT);
    }
    //Solo si la authenticacion attempt fue satisfactoria
   @Override
   protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication auth){
       try {
        UserDetailsImpl userDetails= (UserDetailsImpl) auth.getPrincipal();
        String token= TokenUtils.createToken(userDetails.getName(), userDetails.getUsername());
        System.out.println("************** token **********");
        System.out.println(token);
        System.out.println("*******************************");
        response.addHeader("Authorization", "Bearer " + token);
        response.getWriter().flush();
        super.successfulAuthentication(request, response, chain, auth);
       } catch (IOException e) {
           throw new RuntimeException(e);
       } catch (ServletException e) {
           throw new RuntimeException(e);
       }
   }
}
