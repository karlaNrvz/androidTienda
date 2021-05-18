package com.viamatica.tiendaaplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private final int SPLASH_LENGHT=4000;
    SharedPreferences sharedPref;
    String token = null, pagestart = "0";
    ImageView imgLogo;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        IniciaDatos();
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        imgLogo = findViewById(R.id.imgSplash);
        context = MainActivity.this;

        // moverlo al comprar


    }

    @Override
    protected void onResume() {
        super.onResume();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ConsultaDBusuario();
            }
        },SPLASH_LENGHT);
    }

    public void IniciaDatos(){
        sharedPref = getSharedPreferences("Login", Context.MODE_PRIVATE);
        token = sharedPref.getString("token", "");
        pagestart = sharedPref.getString("pagestart", "0");



        /*Pruebas Encrypt*/
//        Productos productos = new Productos();
//        productos.setId_producto(1);
//        productos.setName("Prueba");
//        productos.setDestacado("Documento");
//
//
//        Gson gson = new Gson();
//        try {
//            String encrypt = Encriptar.encrypt(gson.toJson(productos));
//
//            Productos newProducto = gson.fromJson(Encriptar.decrypt(encrypt),Productos.class);
//
//            Log.v("Prueba",newProducto.getNa

    }


    public void ConsultaDBusuario(){
        if(token == "" || token == null){
            Intent intent = new Intent(getApplicationContext(), InicioSesionActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }else {
            Intent intent = new Intent(getApplicationContext(), NavigationActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
        finish();
    }
}