package com.example.testjwt.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;

@Data
@Table(name= "client")
@Entity
public class Client implements Serializable{

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String username;
    private String password;
    private String email;

}
