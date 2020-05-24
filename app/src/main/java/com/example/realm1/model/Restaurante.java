package com.example.realm1.model;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Restaurante extends RealmObject {

    @PrimaryKey
    private String id;
    private String nombre;
    private String descripcion;
    RealmList<Product> products;

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
    public RealmList<Product> getProtuctos() {
        return products;
    }
    public void setProtuctos(RealmList<Product> products) {
        this.products = products;
    }
}
