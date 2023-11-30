package com.example.ventas.modelos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class AuthUser {
    private String username;
    private String pass;
    private String password;
    private String email;
}
