package com.example.testjwt.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/ini")
public class inicioController {

    @GetMapping(value= "/")
    public String getSaludo(){
        return "Hola tu...";
    }

    @GetMapping(value= "/cli")
    public String getSaludoAdmin(){
       return "Hola cli...";
    }

}
