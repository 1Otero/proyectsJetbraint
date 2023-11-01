package com.example.testsecurity.entitys;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name= "Users")
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name= "id_user")
    private Long idUser;

    @Column(name= "username")
    private String userName;
    @Column(name= "name")
    private String name;

    @Column(name= "mail")
    private String mail;

    @Column(name= "pass")
    private String password;

    @OneToMany(cascade= CascadeType.PERSIST, mappedBy= "user")
    private List<RoleUser> listRoles = new ArrayList<>();

    /*@OneToMany
    private List<Role> listRoles= new ArrayList<>();

    public List<Role> getListRoles(){
        return this.listRoles;
    }
    public void setListRoles(List<Role> list){
        this.listRoles= list;
    }*/

    public String getName(){ return this.name;}
    public void setName(String name){this.name= name;}


    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setListRoles(List<RoleUser> listRoles){this.listRoles= listRoles;}
    public List<RoleUser> getListRoles(){return this.listRoles;}

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
