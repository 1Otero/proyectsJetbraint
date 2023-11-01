package com.example.testsecurity.entitys;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Table(name= "roleuser")
@AllArgsConstructor
@NoArgsConstructor
public class RoleUser {

    @Id
    @Column(name= "id")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long idRoleUser;

    private String describeRole;

    @ManyToOne
    private Role role;

    @ManyToOne
    private User user;

    private Long getIdRoleUser(){return this.idRoleUser;}
    private void setIdRoleUser(Long id){this.idRoleUser= id;}


    public String getDescribeRole() {
        return describeRole;
    }

    public void setDescribeRole(String describeRole) {
        this.describeRole = describeRole;
    }

    public Role getRole(){return this.role;}
    public void setRole(Role role){this.role= role;}

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
