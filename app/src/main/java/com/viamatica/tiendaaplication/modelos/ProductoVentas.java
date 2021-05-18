package com.viamatica.tiendaaplication.modelos;

public class ProductoVentas extends Productos {
    int cantidad;
    float subtotal;

    public ProductoVentas(String id, String descripcion, String precio, String estado, String detalle, String imagen, int cantidad) {
        super(id, descripcion, precio, estado, detalle, imagen);
        this.cantidad = cantidad;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public float getSubtotal() {
        return subtotal*cantidad;
    }
}
