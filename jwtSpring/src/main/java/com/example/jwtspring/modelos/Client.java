package com.example.jwtspring.modelos;

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
    @JsonProperty("clave")
    @Column(name= "clave")
    private String pass;
    //@JsonProperty("pedidos")
    //@Column(name= "listPedido")
    //@OneToMany(mappedBy= "client", cascade= CascadeType.PERSIST)
    //@OneToMany(mappedBy= "client", cascade= CascadeType.ALL, orphanRemoval= true)
    //private List<Pedido> listPedidos;
    @JsonProperty("body")
    private String body;
    @JsonProperty("subject")
    private String subject;
    //private List<String> listAuthoritys= new ArrayList<>();
    //@OneToMany(mappedBy = "id_client", cascade = CascadeType.PERSIST)
    //private List<ClientRole> listRolesClient;/**/
    public String getName(){ return this.name;}
    public void setName(String name){ this.name= name;}
    public String getCorreo() {
        return correo;
    }
    public void setCorreo(String correo) {
        this.correo = correo;
    }
    //public List<Pedido> getListPedidos(){return this.listPedidos;}
    //public void setListPedidos(List<Pedido> listPedidos){this.listPedidos= listPedidos;}
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
    public String getPass() { return pass; }
    public void setPass(String pass) { this.pass = pass; }

    /*public List<ClientRole> getListRolesClient() {
        return listRolesClient;
    }

    public void setListRolesClient(List<ClientRole> listRolesClient) {
        this.listRolesClient = listRolesClient;
    }*/

    /*public List<String> getListAuthoritys() {
        return listAuthoritys;
    }
    public void setListAuthoritys(List<String> listAuthoritys) {
        this.listAuthoritys = listAuthoritys;
    }*/
}
