package com.example.ventas.controlladores;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/admin")
@RestController
public class AdminController {
    @GetMapping("/")
    public String getSaludo(){
        return "a task is good";
    }
}
