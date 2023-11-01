package com.example.testsecurity.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.*;

public class TokenUtils {

    private final static String ACCESS_TOKEN_SECRECT= "4f1nhkfdkskaskfsdkfksal232kksfask";
    //private final static Long ACCESS_TOKEN_VALIDITY_SECOND= 2_592_000L;
    private final static Long ACCESS_TOKEN_VALIDITY_SECOND= 600L;

    public static String crearToken(String username, String mail){
        try{
            Long expirationTime= ACCESS_TOKEN_VALIDITY_SECOND * 2L;
            Date expirationToken= new Date(System.currentTimeMillis() + expirationTime);
            System.out.println("clockSystem");
            System.out.println(new Date(System.currentTimeMillis()));
            System.out.println("expirationToken:");
            System.out.println(expirationTime);
            List<GrantedAuthority> listGrantedAuthority= new ArrayList<>();
            listGrantedAuthority.add(new SimpleGrantedAuthority("ROLE_admin"));
            listGrantedAuthority.add(new SimpleGrantedAuthority("ROLE_tester"));
            Map<String, Object> listClaims= new HashMap<>();
            listClaims.put("nombre", username);
            listClaims.put("email", mail);
            listClaims.put("roles", listGrantedAuthority);
            return Jwts.builder()
                    .setSubject(mail)
                    .setExpiration(expirationToken)
                    .setClaims(listClaims)
                    .signWith(Keys.hmacShaKeyFor(ACCESS_TOKEN_SECRECT.getBytes()))
                    .compact();
        }catch(Exception e){
            System.out.println("Fallo creacion token");
            return null;
        }
    }

    public static UsernamePasswordAuthenticationToken  getAuthentication(String token){
        String mail= "";
        try{
            Claims claim= Jwts.parserBuilder()
                    .setSigningKey(ACCESS_TOKEN_SECRECT.getBytes())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            mail= claim.getSubject();
            List<GrantedAuthority> listGratedAuthority= (List<GrantedAuthority>) claim.get("roles");
            System.out.println("Roles authorization: ");
            listGratedAuthority.stream().forEach(e -> {
                System.out.println(e);
            });
            return new UsernamePasswordAuthenticationToken(mail,null, listGratedAuthority);
        }catch(Exception e){
            System.out.println("Error authentication - Username: " + mail);
            System.out.println(e);
            return null;
        }
    }
}
