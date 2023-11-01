package com.example.ventas.modelos;

import com.fasterxml.jackson.annotation.JsonProperty;

public class fileUp {
    @JsonProperty("name")
    private String name;
    @JsonProperty("originalFilename")
    private String originalFilename;
    @JsonProperty("contentType")
    private String contentType;
    @JsonProperty("content")
    private byte[] content;

    private byte[] bytes;


    public String getOriginalFilename() {
        return originalFilename;
    }

    public void setOriginalFilename(String originalFilename) {
        this.originalFilename = originalFilename;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public byte[] getContent(){return this.content;}
    public void setContent(byte[] content){this.content= content;}

    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
