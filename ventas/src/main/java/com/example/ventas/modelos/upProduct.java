package com.example.ventas.modelos;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

public class upProduct {
    @JsonProperty("name")
    private String name;
    @JsonProperty("img")
    //private MultipartFile upPhoto;
    private fileUp upPhoto;

    private String tipo;
    private String description;
    private double valor;

    public String getName(){return this.name;}
    public void setName(String name){this.name= name;}

    public fileUp getUpPhoto(){return this.upPhoto;}
    public void setUpPhoto(fileUp file){this.upPhoto= file;}


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

}
