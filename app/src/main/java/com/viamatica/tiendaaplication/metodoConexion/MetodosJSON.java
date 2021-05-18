package com.viamatica.tiendaaplication.metodoConexion;


import com.viamatica.tiendaaplication.modelos.Productos;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class MetodosJSON {

   public ArrayList<Productos> jsonProductos(JSONArray json){
        ArrayList<Productos> lista = new ArrayList<Productos>();
        try{
            for(int i=0;i<json.length();i++) {
                JSONObject post = (JSONObject) json.getJSONObject(i);
                Productos item = new Productos();
                item.setId(post.getString("id"));
                item.setDescripcion(post.getString("descripcion"));
                item.setDetalle(post.getString("detalle"));
                item.setEstado(post.getString("estado"));
                item.setPrecio(post.getString("precio"));
                item.setImagen(post.getString("imagen"));
                lista.add(item);
            }
        }catch(JSONException e){
            e.printStackTrace();
            return lista;
        }
        catch (Exception e){
            return lista;
        }
        return lista;
    }



}
