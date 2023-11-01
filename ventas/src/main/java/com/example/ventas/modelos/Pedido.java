package com.example.ventas.modelos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name= "pedidos")
public class Pedido {

    @Id
    //@GeneratedValue(strategy= GenerationType.IDENTITY, generator= "id_pedido")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    //@SequenceGenerator(name = "id_pedido", sequenceName = "id_pedido", allocationSize = 1)
    //@GeneratedValue(strategy= GenerationType.SEQUENCE)
    @Column(name= "id")
    private Long id;

    @JsonProperty("count")
    private int countProducts;
    @JsonProperty("total")
    private int total;

    @JsonProperty("client")
    //one client to many pedidos
    @ManyToOne
    @JoinColumn(name= "id_client")
    private Client client;

    //Este dato que sigue se debe borrar
    /*@JsonProperty("products")
    //@OneToMany(mappedBy= "pedido", cascade= CascadeType.MERGE)
    @OneToMany(mappedBy= "pedido")
    private List<Product> listProduct;*/

    @JsonProperty("productsPedido")
    @OneToMany(mappedBy= "pedido", cascade= CascadeType.ALL, orphanRemoval= true)
    //@OneToMany(mappedBy= "pedido", cascade= CascadeType.PERSIST)
    private List<PedidoProduct> lProduct= new ArrayList<>();

    public void setCountProducts(int count){
        this.countProducts= count;
    }
    public int getCountProducts(){
        return this.countProducts;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public Client getClient(){ return this.client;}
    public void setClient(Client cli){ this.client= cli;}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /*
    public List<Product> getListProduct() {
        return listProduct;
    }
    public void setListProduct(List<Product> listPoduct) {
        this.listProduct = listPoduct;
    }
    */
    public List<PedidoProduct> getlProduct() {
        return lProduct;
    }
    public void setlProduct(List<PedidoProduct> lProduct) {
        this.lProduct = lProduct;
    }
}
