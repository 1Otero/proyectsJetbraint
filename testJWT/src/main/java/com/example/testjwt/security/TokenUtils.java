package com.example.testjwt.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.*;
import java.util.stream.Collectors;

public class TokenUtils {
    //$2a$10$4vBc//OybqTluAjMbxOsw.X3TtwJbAbbfH98WpBqzgPT4Xhju56eu
    private final static String ACCESS_TOKEN_SECRECT= "4qhkldfjsojfdsghsfljSalkjddsE3hWr";
    private final static Long ACCESS_TOKEN_VALIDITY_SECOND= 2_592_000L;
    public static String createToken(String name, String email){
        //SecretKey key= Keys.hmacShaKeyFor(ACCESS_TOKEN_SECRECT.getBytes());
        Long expirationTime= ACCESS_TOKEN_VALIDITY_SECOND * 1_000;
        Date expirationDate= new Date(System.currentTimeMillis() + expirationTime);
        System.out.println(new Date(System.currentTimeMillis() + expirationTime));
        Map<String, Object> extra= new HashMap<>();
        List<GrantedAuthority> lAuthority= new ArrayList<>();
        lAuthority.add(new SimpleGrantedAuthority("admin"));
        lAuthority.add(new SimpleGrantedAuthority("cli"));
        extra.put("nombre", name);
        extra.put("email", email);
        extra.put("roles", lAuthority);
        return Jwts.builder()
                    .setSubject(email)
                    .setExpiration(expirationDate)
                    .addClaims(extra)
                    .signWith(Keys.hmacShaKeyFor(ACCESS_TOKEN_SECRECT.getBytes()))
                    //.signWith(new SecretKeySpec(byteSecret, SignatureAlgorithm.HS256.getJcaName()))
                    .compact();
    }

    public static UsernamePasswordAuthenticationToken getAuthentication(String token){
        try{
            Claims claims= Jwts.parserBuilder()
                    .setSigningKey(ACCESS_TOKEN_SECRECT.getBytes())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            String email= claims.getSubject();
            List<String> ls= (List<String>) claims.get("roles");
            System.out.println("********* Roles: ************");
            System.out.println(ls.size());
            System.out.println("*****************************");
            /*Collection<GrantedAuthority>  granted= ls.stream().map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());*/
            List<GrantedAuthority> listPer= new ArrayList<>();
            listPer.add(new SimpleGrantedAuthority("ROLE_admin"));
            listPer.add(new SimpleGrantedAuthority("ROLE_tester"));
            System.out.println("pasoooo roles! ");
            //return new UsernamePasswordAuthenticationToken(email, null, Collections.EMPTY_LIST);
            return new UsernamePasswordAuthenticationToken(email, null, listPer);
        }catch(Exception e){
            System.out.println("Error authenticando con token");
            System.out.println(e);
            return null;
        }
    }


}
