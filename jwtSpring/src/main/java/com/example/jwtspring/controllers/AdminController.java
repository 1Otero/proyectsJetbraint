package com.example.jwtspring.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/admin")
public class AdminController {

    @GetMapping(value = "/")
    //@PreAuthorize("hasRole('admin')")
    public String saludarAdmin(){
        return "Welcome admin! ";
    }
}
