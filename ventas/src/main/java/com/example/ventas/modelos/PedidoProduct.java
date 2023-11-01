package com.example.ventas.modelos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

@Table(name= "productByOrder")
@Entity
public class PedidoProduct {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name= "id")
    private Long id;

    //@JsonProperty("pedido")
    @ManyToOne
    @JoinColumn(name= "id_pedido")
    private Pedido pedido;

    //@JsonProperty("product")
    @ManyToOne
    @JoinColumn(name= "id_product")
    private Product product;

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
