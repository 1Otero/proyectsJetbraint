package com.example.testjwt.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value= "/admin")
public class AdminController {

    @GetMapping(value= "/")
    public String saludoAdmin(){ return "Hello admin";}
}
