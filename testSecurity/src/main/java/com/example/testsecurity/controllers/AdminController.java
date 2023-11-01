package com.example.testsecurity.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value= "/admin")
public class AdminController {

    @GetMapping(value= "/")
    public String saludo(){
        return "Hello admin to home";
    }

}
