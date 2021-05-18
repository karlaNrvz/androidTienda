package com.viamatica.tiendaaplication.metodoConexion;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


import com.viamatica.tiendaaplication.modelos.ProductoVentas;
import com.viamatica.tiendaaplication.modelos.Productos;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class BDHelper extends SQLiteOpenHelper {

    SQLiteDatabase dbWrite;
//    final String DbNanme ="BDApp";
//    final int Version = 1;
    public BDHelper(Context context, String name,
                    SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        // admin = new BDHelper(getContext(), "BDFesa.db", null, 1);
        // admin = new BDHelper(getContext(), "BDFesa.db", null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //db.execSQL("drop table productoCompra");
        db.execSQL("create table if not exists productoCompra(id TEXT primary key, Descripcion TEXT, cantidad integer, precio TEXT,  imagen TEXT) ");
        //db.execSQL("insert into usuarios values(null,'admin','admin')");
        Log.v("Create Databae","Creada con exito");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int
            oldVersion, int newVersion) {
        //db.execSQL("create table usuarios(codigo integer primary key autoincrement, usuario text, contrasena text) ");
        //db.execSQL("insert into usuarios values(null,'admin','admin')");
    }

    public ArrayList<ProductoVentas> getCarrito(SQLiteDatabase db){
        dbWrite = db;
        ArrayList<ProductoVentas> venta = new ArrayList<>();

        String id;
        Integer Cantidad;
        float precio;

        String Descripcion;
        String imagen;

        Cursor c = dbWrite.rawQuery("select id, cantidad, Descripcion, precio, imagen from productoCompra",null);
        if(c != null){
            c.moveToFirst();
            Log.v("GetCarrito:", String.valueOf(c.getCount()));
            Log.v("GetCarrito:", String.valueOf(c.getColumnNames()));
            if(c.getCount() > 0) {
            do{
                id = c.getString(c.getColumnIndex("id"));

                Cantidad = c.getInt(c.getColumnIndex("cantidad"));
                precio = c.getFloat(c.getColumnIndex("precio"));

                imagen = c.getString(c.getColumnIndex("imagen"));
                Descripcion = c.getString(c.getColumnIndex("Descripcion"));


                venta.add(new ProductoVentas(id.toString(),Descripcion,String.valueOf(precio),"","",imagen,Cantidad));

            }while (c.moveToNext());
            }
        }
        c.close();
        return venta;
    }





    public Boolean AddProductoCarrito( SQLiteDatabase db,Productos e, Integer Cantidad){
        boolean b = false;
        try {
            dbWrite = db;
            String id = e.getId();
            String precio = e.getPrecio();

            String Descripcion = e.getDescripcion();
            String imagen = e.getImagen();

            Cursor c = dbWrite.rawQuery("select id, cantidad, Descripcion, precio, imagen from productoCompra where id = ?",new String[]{String.valueOf(id)});
            if(c != null){
                c.moveToFirst();
                if(c.getCount() == 1){

                    Log.v("ExecSql","query Si Update");
                    ContentValues values = new ContentValues();
                    values.put("cantidad",Cantidad);
                    db.update("productoCompra",values,"id = ?",new String[]{String.valueOf(id)});
                    c.close();
                    return true;
                }else if(c.getCount()>1){
                    Log.v("AddProducto","Existen mas de 1 producto ingresado");
                    return false;
                }
            }
            c.close();

            Log.v("ExecSql","query Si Insert");
            ContentValues values = new ContentValues();
            values.put("id",id);
            values.put("cantidad",Cantidad);
            values.put("precio",precio);
            values.put("Descripcion",Descripcion);
            values.put("imagen",imagen);

            db.insert("productoCompra",null,values);
            b = true;

        } catch (Exception ex) {
            ex.getStackTrace();
        }
        return b;
    }

    public Boolean deleteAllCarrito(SQLiteDatabase db){
        dbWrite = db;
      boolean  b = false;
        try {
                // valida si agrega o se actualiza la cantidad
                //dbWrite.execSQL("delete from productoCompra",null);
            db.delete("productoCompra","",null);

            b = true;
        } catch (Exception e) {
            e.getStackTrace();
        }
        return b;
    }

    public Boolean deleteItemCarrito(SQLiteDatabase db,String Codigo){
        dbWrite = db;
        boolean  b = false;
        try {
            //String query = String.format("delete from productoCompra where codigo = %s",Codigo);
            // valida si agrega o se actualiza la cantidad
            //Log.v("ExecSql",query);
            //dbWrite.execSQL(query);
            db.delete("productoCompra","id = ?",new String[]{String.valueOf(Codigo)});
            b = true;
        } catch (Exception e) {
            e.getStackTrace();
        }
        return b;
    }

}
