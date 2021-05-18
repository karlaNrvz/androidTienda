package com.viamatica.tiendaaplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.google.android.material.snackbar.Snackbar;
import com.viamatica.tiendaaplication.metodoConexion.ConstantesUrl;
import com.viamatica.tiendaaplication.metodoConexion.Hash;
import com.viamatica.tiendaaplication.metodoConexion.MetodosConexion;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.security.MessageDigest;
import java.util.ArrayList;

import static android.view.View.GONE;

public class InicioSesionActivity extends AppCompatActivity {
EditText edtMail,edtPass;
String usuario, clave, nombre,telefono, correo,token;
Button btnLogin;
String con;
ProgressBar progressLogin;
Context context;

String auntenticarUsuario= "autenticarUsuario";
SharedPreferences sharedPref;

ConstantesUrl constantesUrl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_sesion);
        inicializarComp();
    }

    public void inicializarComp(){
        context = InicioSesionActivity.this;
        edtMail = (EditText)findViewById(R.id.edtMail);
        edtPass = (EditText)findViewById(R.id.edtPass);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        progressLogin =(ProgressBar) findViewById(R.id.progressLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             // validacionSesion();
//
                if(validarDatos()) {
                    Intent intent = new Intent(getApplicationContext(), NavigationActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }else{

                    Toast toast = Toast.makeText(context, "Por favor, Debe llenar todos los campos.", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
            }

        });

    }


    public void validacionSesion(){
        if(validarDatos()){
            try {
                if (existeConexion()) {

                    usuario = edtMail.getText().toString();
                    clave = edtPass.getText().toString();
                    con=    Hash.sha1(clave);
                    Log.v("clave", con);

                    new InicioSesion().execute(auntenticarUsuario,usuario,con);
                } else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        Snackbar.make(findViewById(R.id.relaInicio), "Sin conexión a internet!", Snackbar.LENGTH_LONG)
                                .setAction("conectar", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        startActivity(new Intent(WifiManager.ACTION_PICK_WIFI_NETWORK));
                                    }
                                }).setActionTextColor(getColor(R.color.purple_500)).show();
                    }

                    InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);

                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }


    }


    private boolean existeConexion(){
        //Pregunta si el telefono tiene conexion a internet
        ConnectivityManager connectivityManager=(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();

        return networkInfo!=null&&networkInfo.isConnected();
    }
    public boolean validarDatos(){

        if(edtMail.getText().toString().matches("") || edtPass.getText().toString().matches("")){
            Toast toast = Toast.makeText(this, "Por favor, Debe llenar todos los campos.", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            return  false;
        }
        else if (edtMail.getText().toString().matches("")) {
            Toast.makeText(this, "Por favor, Ingrese su Usuario.", Toast.LENGTH_SHORT).show();
            return  false;
        }

        else if (edtPass.getText().toString().matches("")) {
            Toast toast = Toast.makeText(this, "Por favor, Ingrese su Clave", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            return  false;
        }
        return true;
    }

    Object jsonDatos(String transcicion,String usuario, String clave){

        JSONObject js = new JSONObject();
        try {
            js.put("transicion" ,transcicion);

            JSONObject data = new JSONObject();
            data.put("username", usuario);
            data.put("password", clave);
            js.put("datosUsuario",data);


        } catch (JSONException e) {
            return "";
        }catch (Exception e){
            return "";

        }
        return js;
    }
    private void savePreferences(){

        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("token",token);
        editor.putString("nombre",nombre);
        editor.putString("email",correo);
        editor.apply();

    }

    private class InicioSesion extends AsyncTask<String, String, String> {
        private ProgressDialog progressDialog = new ProgressDialog(context);
        @Override
        protected void onPreExecute() {
          progressLogin  .setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... params) {
            Log.v("ProductosFragment","Inicio de Metodo.");
            String result = "";
            JSONObject jsonO = null;
            JSONArray JSONOarray = null;
            JSONObject jsonData = null;
            MetodosConexion metodosc = new MetodosConexion();
           // MetodosJSON metodJ = new MetodosJSON();
           /// ArrayList<Cabecera> cabecera = new ArrayList<Cabecera>();
            auntenticarUsuario = params[0];
            usuario = params[1];
            con = params[2];
            ///cabecera.add(new Cabecera("usuario",usuario));
            //cabecera.add(new Cabecera("clave",clave));
            //String token = access_token;
            String metodo = constantesUrl.getBaseURL();
            String metodo2 = constantesUrl.getUsuarios();

            try {

                jsonO = metodosc.getJSONObject(constantesUrl.getBaseURL() +  constantesUrl.getUsuarios(), "POST","{}", "",jsonDatos(auntenticarUsuario,usuario,clave).toString(),"");
                Log.v("jsonInicio", String.valueOf(jsonO));

                if (jsonO != null) {

                    JSONObject jsonObectInformacion = new JSONObject(jsonO.getString("usuario"));
                    //  existeError = jsonObectInformacion.getString("existeError");
                        JSONObject jsonObjectData = new JSONObject(jsonO.getString("usuario"));
                        token = jsonObjectData.getString("token");
                        correo = jsonObjectData.getString("email");
                        nombre = jsonObjectData.getString("nombre");
                        telefono = jsonObjectData.getString("apellido");
                        sharedPref = getSharedPreferences("Login", Context.MODE_PRIVATE);
                        savePreferences();
                        result = "true";

                }else{
                    result = "";
                }

                return result;
            } catch (IOException e) {
                return ""; }
            catch (JSONException e) {
                return ""; }
        }
        @Override
        protected void onPostExecute(String respuesta) {
            progressLogin.setVisibility(GONE);
            super.onPostExecute(respuesta);
            // cargarListaProducto();
            Log.v("okResult", "ok");
            Log.v("okResult", "ok");

            if(respuesta.equals("true")) {
                Intent intent = new Intent(getApplicationContext(), NavigationActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);

            }else if(respuesta.equals("true")){



            }
            else{

            }
        }
    }


}