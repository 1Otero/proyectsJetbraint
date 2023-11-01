package com.example.reecuentromascota.controllers;

import jakarta.annotation.security.PermitAll;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;

@Controller
@RequestMapping(value = "/api/ini")
public class InicioController {

    public InicioController(){
        System.out.println("Ejecutando inicioController");
    }

    @GetMapping(value = "/")
    //public ModelAndView bienvenido(HttpServletRequest req, HttpServletResponse res){
    public String bienvenido(HttpServletRequest req, HttpServletResponse res){
        System.out.println(req.getRequestURL() + " Host: " + req.getRemoteHost());
        System.out.println("Ingreso usuario" + " SId " + req.getRequestedSessionId());
        ModelAndView m = new ModelAndView();
        m.setViewName("Index");
        //System.out.println("Con ModelAndView");
        System.out.println("Con solo String");
        //return m;
        return "Index";
    };
    @GetMapping(value = "/test")
    public String test(HttpServletRequest req, HttpServletResponse res){
        System.out.println("desde test");
        return "Index";
    }
    @GetMapping(value = "/testt")
    public String testt(HttpServletRequest req, HttpServletResponse res){
        System.out.println("desde testt");
        Cookie c= new Cookie("fff","fffff");
        res.addCookie(c);
        System.out.println("N Cookies: ".concat(String.valueOf(Arrays.stream(req.getCookies()).count())));
        return "Index";
    }
}
