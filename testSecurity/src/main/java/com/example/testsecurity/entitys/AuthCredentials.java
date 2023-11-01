package com.example.testsecurity.entitys;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class AuthCredentials {

    private String userName;
    private String mail;
    private String password;

}
