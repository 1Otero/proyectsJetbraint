package com.example.testsecurity.entitys;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name= "roles")
@NoArgsConstructor
@AllArgsConstructor
public class Role {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long idRole;

    private String name;

    /*@ManyToOne
    private User idUser;*/

    @OneToMany(mappedBy= "role", cascade = CascadeType.PERSIST)
    private List<RoleUser> roleUser= new ArrayList<>();


    public Long getIdRole() {
        return idRole;
    }

    public void setIdRole(Long idRole) {
        this.idRole = idRole;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<RoleUser> getRoleUser(){return this.roleUser;}
    public void setRoleUser(List<RoleUser> roleUser){this.roleUser= roleUser;}

}
