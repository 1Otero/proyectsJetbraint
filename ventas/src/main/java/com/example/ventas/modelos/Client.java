package com.example.ventas.modelos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.util.List;

@Table(name = "clients")
@Entity
public class Client {

    @JsonProperty("id")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Id
    @Column(name= "id")
    private Long id;
    @JsonProperty("name")
    @Column(name= "name")
    private String name;
    @JsonProperty("mail")
    @Column(name= "mail")
    private String correo;

    @JsonProperty("pedidos")
    @Column(name= "listPedido")
    @OneToMany(mappedBy= "client", cascade= CascadeType.PERSIST)
    //@OneToMany(mappedBy= "client", cascade= CascadeType.ALL, orphanRemoval= true)
    private List<Pedido> listPedidos;

    @JsonProperty("body")
    private String body;

    @JsonProperty("subject")
    private String subject;

    public String getName(){ return this.name;}
    public void setName(String name){ this.name= name;}

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }
    public List<Pedido> getListPedidos(){return this.listPedidos;}
    public void setListPedidos(List<Pedido> listPedidos){this.listPedidos= listPedidos;}

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getSubject(){
        return this.subject;
    }

    public void setSubject(String subject){
        this.subject= subject;
    }
}
