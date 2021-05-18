package com.viamatica.tiendaaplication.modelos;

import java.io.Serializable;

public class Productos implements Serializable {
    String id;
    String descripcion;
    String precio;
    String estado;
    String detalle;
    String imagen;
    public Productos(String id, String descripcion, String precio,String estado, String detalle,String imagen) {
        this.id = id;
        this.descripcion = descripcion;
        this.precio = precio;
        this.estado = estado;
        this.detalle = detalle;
        this.imagen = imagen;
    }

    public  Productos(){}

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }
}
