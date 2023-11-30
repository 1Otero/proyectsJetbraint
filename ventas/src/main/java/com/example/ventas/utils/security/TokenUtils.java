package com.example.ventas.utils.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.*;

public class TokenUtils {
    //private final static String ACCESS_TOKEN_SECRECT= "4f1ndfdsfefshsdfsfk123fssfdsafstr";
    private final static String ACCESS_TOKEN_SECRECT="4qhkldfjsojfdsghsfljSalkjddsE3hWr";
    private final static Long ACCESS_TOKEN_VALIDITY_SECOND= 2_592_000L;
    public static String crearToken(String usermail, String pass){
        try{
          Long expirationTime= ACCESS_TOKEN_VALIDITY_SECOND * 1_00L;
          Date expirationToken= new Date(System.currentTimeMillis() + expirationTime);
          List<GrantedAuthority> listAuthoritys= new ArrayList<>();
          listAuthoritys.add(new SimpleGrantedAuthority("ROLE_CLI"));
          listAuthoritys.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
          Map<String, Object> listClaims= new HashMap<>();
          listClaims.put("username", usermail);
          listClaims.put("email", usermail);
          listClaims.put("Authoritys", listAuthoritys);
          return Jwts.builder()
                  .setSubject(usermail)
                  .setExpiration(expirationToken)
                  .addClaims(listClaims)
                  .signWith(Keys.hmacShaKeyFor(ACCESS_TOKEN_SECRECT.getBytes()))
                  .compact();
        }catch(Exception e){
            System.out.println("Error token utils - crearToken");
            e.fillInStackTrace();
            return null;
        }
    }
    public static UsernamePasswordAuthenticationToken getAuthentication(String token){
      try{
         Claims claim= Jwts.parserBuilder()
                 .setSigningKey(ACCESS_TOKEN_SECRECT.getBytes())
                 .build()
                 .parseClaimsJws(token)
                 .getBody();
         String mail= claim.getSubject();
         List<String> listAuthoritys= (List<String>) claim.get("Authoritys");
         List<GrantedAuthority> ls= new ArrayList<>();
         ls.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
         return new UsernamePasswordAuthenticationToken("truj@gmail.com", null, ls);
      }catch(Exception e){
          System.out.println("Error TokenUtils - getAuthentication");
          System.out.println(e);
          e.fillInStackTrace();
          return null;
      }
    }
}
