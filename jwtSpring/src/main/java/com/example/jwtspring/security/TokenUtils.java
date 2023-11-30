package com.example.jwtspring.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.*;

public class TokenUtils {
    private final static String ACCESS_TOKEN_SECRECT= "4f1ndfdsfefshsdfsfk123fssfdsafstr";
    private final static Long ACCESS_TOKEN_VALIDITY_SECOND= 2_592_000L;
    public static String crearToken(String username, String mail){
        try{
          Long expirationTime= ACCESS_TOKEN_VALIDITY_SECOND * 2L;
          Date expirationToken= new Date(System.currentTimeMillis() + expirationTime);
          List<GrantedAuthority> listAuthoritys= new ArrayList<>();
          listAuthoritys.add(new SimpleGrantedAuthority("ROLE_CLI"));
          listAuthoritys.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
          Map<String, Object> listClaims= new HashMap<>();
          listClaims.put("username", username);
          listClaims.put("email", mail);
          listClaims.put("Authoritys", listAuthoritys);
          return Jwts.builder()
                  .setSubject(mail)
                  .setExpiration(expirationToken)
                  .setClaims(listClaims)
                  .signWith(Keys.hmacShaKeyFor(ACCESS_TOKEN_SECRECT.getBytes()))
                  .compact();
        }catch(Exception e){
            System.out.println("Error token utils - crearToken");
            e.fillInStackTrace();
            return null;
        }
    }
    public static UsernamePasswordAuthenticationToken getAuthentication(String token){
      String mail= "";
      try{
         Claims claim= Jwts.parserBuilder()
                 .setSigningKey(ACCESS_TOKEN_SECRECT.getBytes())
                 .build()
                 .parseClaimsJws(token)
                 .getBody();
         mail= claim.getSubject();
         List<GrantedAuthority> listAuthoritys= (List<GrantedAuthority>) claim.get("Authoritys");
         return new UsernamePasswordAuthenticationToken(mail, null, listAuthoritys);
      }catch(Exception e){
          System.out.println("Error TokenUtils - getAuthentication");
          e.fillInStackTrace();
          return null;
      }
    }
}
