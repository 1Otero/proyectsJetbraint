package com.example.ventas.utils;

import com.example.ventas.modelos.Pedido;

import java.util.List;

public class infoMails {
    private String name;
    private String correo;
    private String subject;
    private String body;
    private Pedido infoPedido;
    private List<String> mails;

    private  String from= "trujilloivanzx@gmail.com";

    private String host= "smtp.gmail.com";

    private String pass= "zjrp oiat tfma yvgm";

    //private String port= "587";
    private String port= "25";

    public String getName(){
        return this.name;
    }
    public void setName(String name){
        this.name= name;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Pedido getInfoPedido() {
        return infoPedido;
    }

    public void setInfoPedido(Pedido infoPedido) {
        this.infoPedido = infoPedido;
    }

    public List<String> getMails(){
        return this.mails;
    }

    public void setMails(List<String> mails){
        this.mails= mails;
    }

    public String getFrom(){
        return this.from;
    }

    public String getHost(){
        return this.host;
    }

    public String getPass(){
        return this.pass;
    }

    public String getPort() {
        return this.port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
