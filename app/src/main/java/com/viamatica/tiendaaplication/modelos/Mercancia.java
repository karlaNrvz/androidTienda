package com.viamatica.tiendaaplication.modelos;

import java.io.Serializable;
import java.util.ArrayList;

public class Mercancia implements Serializable {
String codigoRetorno;
String mensajeRetorno;
ArrayList<Productos> data =new ArrayList<Productos>();

    public String getCodigoRetorno() {
        return codigoRetorno;
    }

    public void setCodigoRetorno(String codigoRetorno) {
        this.codigoRetorno = codigoRetorno;
    }

    public String getMensajeRetorno() {
        return mensajeRetorno;
    }

    public void setMensajeRetorno(String mensajeRetorno) {
        this.mensajeRetorno = mensajeRetorno;
    }

    public ArrayList<Productos> getData() {
        return data;
    }

    public void setData(ArrayList<Productos> data) {
        this.data = data;
    }
}
