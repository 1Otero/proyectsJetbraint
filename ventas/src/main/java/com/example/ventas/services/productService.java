package com.example.ventas.services;

import com.example.ventas.modelos.Product;
import com.example.ventas.repositorys.productRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class productService{

    private productRepository product;

    public productService(productRepository prod){
        this.product= prod;
    }

    public Product saveProduct(Product prod){
        return this.product.save(prod);
    }

    public List<Product> getAllProducts(){
        return this.product.findAll();
    }


}
