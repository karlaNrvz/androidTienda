package com.viamatica.tiendaaplication.metodoConexion;

public class ConstantesUrl {
    // Indica el servidor al cual se apunta
    // (NO olvidarse de modificar el nombre de la app tambien)

    public static final String baseURL = "https://rolimapp.com:3000/";

    // producto por categoria
    public static final String usuarios = "usuarios";
    public static final String productos = "productos";

    public static String getProductos() {
        return productos;
    }

    public static String getBaseURL() {
        return baseURL;
    }

    public static String getUsuarios() {
        return usuarios;
    }
}
