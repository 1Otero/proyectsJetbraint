package com.example.testsecurity.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(value= "/ini")
@RestController
public class InicioController {

    @GetMapping(value= "/")
    public String saludo(){
        return "Hello to ini";
    }


}
