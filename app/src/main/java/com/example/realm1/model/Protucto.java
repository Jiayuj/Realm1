package com.example.realm1.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Protucto extends RealmObject {

    @PrimaryKey
    private String id;
    private String nombre;
    private String descripcion;
    private String precio;
    private String urlImage;

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    public String getPrecio() {
        return precio;
    }
    public void setPrecio(String precio) {
        this.precio = precio;
    }
    public String getUrlImage() {
        return urlImage;
    }
    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

}
