package com.example.ventas.modelos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Nullable;
import java.util.List;

@Table(name= "products")
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name= "id")
    private Long id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("photo")
    @Column(name= "image", length= 1000000)
    private byte[] photo;
    @JsonProperty("cantidad")
    private int cantidad;
    @JsonProperty("precio")
    private double precio;

    /*@JsonProperty("pedido")
    @ManyToOne
    @JoinColumn(name="id_pedido")
    private Pedido pedido;*/


    //@OneToMany(mappedBy= "product", cascade= CascadeType.ALL, orphanRemoval= true, fetch = FetchType.LAZY)
    @OneToMany(mappedBy= "product", cascade= CascadeType.ALL)
    @Column(name= "listPedidos")
    private List<PedidoProduct> listPedidos;

    public Product(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name){
        this.name= name;
    }

    public String getName(){
        return this.name;
    }

    public byte[] getPhoto() {return photo;}

    public void setPhoto(byte[] photo) {this.photo = photo;}

    public int getCantidad(){return this.cantidad;}

    public void setCantidad(int cantidad){ this.cantidad= cantidad;}


    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

/*    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public List<PedidoProduct> getListPedidos() {
        return listPedidos;
    }

    public void setListPedidos(List<PedidoProduct> listPedidos) {
        this.listPedidos = listPedidos;
    }*/
}
