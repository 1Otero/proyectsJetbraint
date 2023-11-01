package com.example.jwtdos.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RequestMapping(value = "/api/ini")
@RestController
//@Controller
public class InicioController {

    //@ResponseBody
    @GetMapping(value = "/")
    public String Saludo(){
        //ModelAndView m = new ModelAndView();
        return "Welcome Sr customers";
    }
    private SecurityContextRepository securityContextRepository =
            new HttpSessionSecurityContextRepository();

    @GetMapping(value = "/login")
    public String login(HttpServletRequest req, HttpServletResponse res){
        String token= "nada aun";
        List<GrantedAuthority> lGrantedA= new ArrayList<>();
        GrantedAuthority grantedAuthority= new SimpleGrantedAuthority("admin");
        lGrantedA.add(grantedAuthority);
        Authentication tokenn = UsernamePasswordAuthenticationToken.authenticated("roro", "1234", lGrantedA);

        //Authentication authenticationU = authenticationManager.authenticate(tokenn);
        //Authentication authentication = new TestingAuthenticationToken("ivad","1234","admin");
        //SecurityContext context = securityContextHolderStrategy.createEmptyContext();
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(tokenn);
        //context.setAuthentication(authentication);
        //securityContextHolderStrategy.setContext(context);
        SecurityContextHolder.setContext(context);
        securityContextRepository.saveContext(context, req, res);
        //System.out.println(authentication.isAuthenticated() + ". ");
        System.out.println(tokenn.isAuthenticated() + ". ");
        return "Beans ".concat(token);
    }
}
