package com.example.ventas.services;

import com.example.ventas.modelos.Client;
import com.example.ventas.modelos.Pedido;
import com.example.ventas.modelos.PedidoProduct;
import com.example.ventas.modelos.Product;
import com.example.ventas.repositorys.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Service
public class clientService {

    @Autowired
    private clientRepository cliente;
    @Autowired
    private productRepository product;

    public clientService(clientRepository cli){
        this.cliente= cli;
    }
    public void testService(){
        System.out.println("_____________________");
        System.out.print("client service: ");
        System.out.println(cliente.findAll());
        System.out.println("_____________________");
    }

    @Transactional
    public Client testSave(Client cli){
        System.out.println("*************************");
        System.out.println("Client saved: ");


        cli.getListPedidos().stream().forEach(e -> e.setClient(cli));

        //descomentar *******************************
        //Product p= cli.getListPedidos().get(0).getlProduct().get(0).getProduct();
        //System.out.println("***********1*********");
        //System.out.println(p.getName());
        //*******************************************
        //Product prod= this.product.save(p);

        System.out.println("*********2**********");
        Product p2= product.findById(1l).get();
        System.out.println(p2.getName());
        System.out.println("*********2**********");
        PedidoProduct pedProd;

        int ii= 0;

        //cli.getListPedidos().stream().forEach(e -> e.setlProduct(new ArrayList<PedidoProduct>()));

        for(Pedido pedido: cli.getListPedidos()){

            /*for(PedidoProduct product: pedido.getlProduct()){
                product.setId(prod.getId());
                product.setName(prod.getName());
                product.setPhoto(prod.getPhoto());
                product.setCantidad(prod.getCantidad());
                product.setPrecio(prod.getPrecio());
                //product.setPedido(pedido);
                product.setPedido(pedido);

                product.getPedido().setTotal(1000);
                System.out.println(product.getPedido().getTotal());
                System.out.println("^^^^^^^^^^^^^^^^^^");
                //pedido.getListProduct().add(product);
            }*/

            pedProd= new PedidoProduct();
            pedProd.setProduct(p2);
            pedProd.setPedido(pedido);

            System.out.println(pedido.getTotal());
            System.out.println(p2.getName());
            pedido.getlProduct().add(pedProd);

            System.out.println(ii);
            ii++;

            //pedido.getListProduct().stream().forEach(e -> e.setPedido(pedido));

        }

        //System.out.println(p.getId());
        System.out.println("***********1*********");


        Client c= cliente.save(cli);
        System.out.println(c);
        System.out.println("*************************");
        return c;
    }

    public Client saveCli(Client cli){
        Client c= cliente.save(cli);
        System.out.println(c);
        System.out.println("*************************");
        return c;
    }
}
