package com.example.ventas.modelos;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Table(name = "client_role")
@Entity
public class ClientRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_client_role")
    private int ClientRole;
    @ManyToOne
    @JoinColumn(name = "id_client")
    private Client id_client;
    @ManyToOne
    @JoinColumn(name = "id_role")
    private Role id_role;

    public Client getId_client() {
        return id_client;
    }

    public void setId_client(Client id_client) {
        this.id_client = id_client;
    }

    public Role getId_role() {
        return id_role;
    }

    public void setId_role(Role id_role) {
        this.id_role = id_role;
    }
}
