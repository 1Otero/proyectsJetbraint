package com.example.testjwt.entity;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

@Data
public class authCredentials {
    private String email;
    private String password;
    private List<GrantedAuthority> Colletions;
}
