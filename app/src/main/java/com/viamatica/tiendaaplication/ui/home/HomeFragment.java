package com.viamatica.tiendaaplication.ui.home;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.viamatica.tiendaaplication.Fragments.DetalleFragment;
import com.viamatica.tiendaaplication.NavigationActivity;
import com.viamatica.tiendaaplication.R;
import com.viamatica.tiendaaplication.metodoConexion.ConstantesUrl;
import com.viamatica.tiendaaplication.metodoConexion.MetodosConexion;
import com.viamatica.tiendaaplication.metodoConexion.MetodosJSON;
import com.viamatica.tiendaaplication.modelos.Productos;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import Adapters.MercanciaAdapter;

import static android.view.View.GONE;

public class HomeFragment extends Fragment {
    Context context;
    private HomeViewModel homeViewModel;
    ProgressBar progressHome;
    ConstantesUrl constantesUrl;
    String transaccion ="generico";
    String tipo ="4";
    Button btnCarro;
    RecyclerView rcvMercancia;
    ArrayList<Productos> listaMercancia = new ArrayList();
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        return root;
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        inicializarComponentes(view);
    }

    public void inicializarComponentes(View view){
        context = getActivity();
        rcvMercancia= (RecyclerView) view.findViewById(R.id.rcvMercancia);
        progressHome = (ProgressBar) view.findViewById(R.id.progressHome);
        new productos().execute(transaccion,tipo);

//        Productos pedido1= new Productos("27","EDREDÓN HASTA 1 PLZ","7","true","El edredón es hasta máximo 2 plazas, es lavado en agua, con productos especiales de cuidado de tejidos para ropa de cama","https://images.deprati.com.ec/sys-master/images/h8b/hdc/9942860922910/15847893-0_product_1200Wx1800H");
//        Productos pedido2= new Productos("28","EDREDÓN HASTA 2 PLZ","7","true","El edredón es hasta máximo 2 plazas, es lavado en agua, con productos especiales de cuidado de tejidos para ropa de cama","https://images.deprati.com.ec/sys-master/images/h8b/hdc/9942860922910/15847893-0_product_1200Wx1800H");
//        Productos pedido3= new Productos("29","EDREDÓN HASTA 3 PLZ","7","true","El edredón es hasta máximo 2 plazas, es lavado en agua, con productos especiales de cuidado de tejidos para ropa de cama","https://images.deprati.com.ec/sys-master/images/h8b/hdc/9942860922910/15847893-0_product_1200Wx1800H");
//        listaMercancia.add(pedido1);
//        listaMercancia.add(pedido2);
//        listaMercancia.add(pedido3);
//        //Log.v("item1",listaMercancia.get(0).getDescripcion());
//        cargarProductos();

    }


    Object jsonDatos(String transcicion, String tipo){

        JSONObject js = new JSONObject();
        try {
            js.put("transicion" ,transcicion);
            js.put("tipo", tipo);


        } catch (JSONException e) {
            return "";
        }catch (Exception e){
            return "";

        }
        return js;
    }

    private JSONObject httpsProducto() {
    String json = "{\n" +
            "    \"codigoRetorno\": \"0001\",\n" +
            "    \"mensajeRetorno\": \"Consulta Ok\",\n" +
            "    \"data\": [\n" +
            "        {\n" +
            "            \"id\": 44,\n" +
            "            \"descripcion\": \"TERNO 2 PIEZAS SECO\",\n" +
            "            \"precio\": \"8.5\",\n" +
            "            \"estado\": true,\n" +
            "            \"detalle\": \"El terno de 2 piezas incluye la leva y el pantalón, el lavado es en seco, se entrega en armador y con su respectiva funda.\",\n" +
            "            \"imagen\": \"seco-terno2.jpg\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": 45,\n" +
            "            \"descripcion\": \"TERNO 3 PIEZAS SECO\",\n" +
            "            \"precio\": \"9.5\",\n" +
            "            \"estado\": true,\n" +
            "            \"detalle\": \"El terno de 3 piezas incluye la leva, el chaleco y el pantalón, el lavado es en seco,  se entrega en armador y con su respectiva funda.\",\n" +
            "            \"imagen\": \"seco-terno3.jpeg\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": 46,\n" +
            "            \"descripcion\": \"LEVA SECO\",\n" +
            "            \"precio\": \"4.75\",\n" +
            "            \"estado\": true,\n" +
            "            \"detalle\": \"La leva es lavada en seco  con el mayor cuidado de la prenda, se entrega en armador y con su respectiva funda.\",\n" +
            "            \"imagen\": \"seco-leva.jpeg\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": 47,\n" +
            "            \"descripcion\": \"CAMISA SECO\",\n" +
            "            \"precio\": \"3\",\n" +
            "            \"estado\": true,\n" +
            "            \"detalle\": \"La camisa es lavada en seco  con el mayor cuidado de la prenda, se entrega en armador y con su respectiva funda.\",\n" +
            "            \"imagen\": \"seco-camisa.jpg\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": 48,\n" +
            "            \"descripcion\": \"CORBATA SECO\",\n" +
            "            \"precio\": \"2.65\",\n" +
            "            \"estado\": true,\n" +
            "            \"detalle\": \"La corbata es lavada en seco  con el mayor cuidado de la prenda, se entrega en armador y con su respectivo portacobata.\",\n" +
            "            \"imagen\": \"seco-corbata.jpeg\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": 49,\n" +
            "            \"descripcion\": \"BLUSA SECO\",\n" +
            "            \"precio\": \"3\",\n" +
            "            \"estado\": true,\n" +
            "            \"detalle\": \"La blusa es lavada en seco  con el mayor cuidado de la prenda, se entrega en armador y con su respectiva funda.\",\n" +
            "            \"imagen\": \"seco-blusa.jpeg\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": 50,\n" +
            "            \"descripcion\": \"CHAQUETA SECO\",\n" +
            "            \"precio\": \"4.75\",\n" +
            "            \"estado\": true,\n" +
            "            \"detalle\": \"La chaqueta es lavada en seco  con el mayor cuidado de la prenda, se entrega en armador y con su respectiva funda.\",\n" +
            "            \"imagen\": \"seco-chaqueta.jpeg\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": 51,\n" +
            "            \"descripcion\": \"ABRIGO PESADO\",\n" +
            "            \"precio\": \"7.75\",\n" +
            "            \"estado\": true,\n" +
            "            \"detalle\": \"El abrigo es lavado en seco  con el mayor cuidado de la prenda, se entrega en armador y con su respectiva funda.\",\n" +
            "            \"imagen\": \"seco-abrigo.jpg\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": 52,\n" +
            "            \"descripcion\": \"VESTIDO CORTO SECO\",\n" +
            "            \"precio\": \"6\",\n" +
            "            \"estado\": true,\n" +
            "            \"detalle\": \"El vestido es lavado en seco con el mayor cuidado de la prenda, se entrega en armador y con su respectiva funda.\",\n" +
            "            \"imagen\": \"seco-vestidocorto.jpg\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": 53,\n" +
            "            \"descripcion\": \"VESTIDO LARGO SECO\",\n" +
            "            \"precio\": \"10.5\",\n" +
            "            \"estado\": true,\n" +
            "            \"detalle\": \"El vestido es lavado en seco con el mayor cuidado de la prenda, se entrega en armador y con su respectiva funda.\",\n" +
            "            \"imagen\": \"seco-vestidolargo.jpg\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": 54,\n" +
            "            \"descripcion\": \"VESTIDO NOVIA SIN COLA\",\n" +
            "            \"precio\": \"30\",\n" +
            "            \"estado\": true,\n" +
            "            \"detalle\": \"El vestido es lavado en seco con el mayor cuidado de la prenda, se entrega en armador y con su respectiva funda.\",\n" +
            "            \"imagen\": \"seco-vestidonovia.jpg\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"id\": 55,\n" +
            "            \"descripcion\": \"VESTIDO NOVIA CON COLA\",\n" +
            "            \"precio\": \"45\",\n" +
            "            \"estado\": true,\n" +
            "            \"detalle\": \"El vestido es lavado en seco con el mayor cuidado de la prenda, se entrega en armador y con su respectiva funda.\",\n" +
            "            \"imagen\": \"seco-vestidonoviacola.jpg\"\n" +
            "        }\n" +
            "    ]\n" +
            "}";

        JSONObject oJson = null;
        try {
            oJson = new JSONObject(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return oJson;
    }

    public void cargarProductos(){
        rcvMercancia.setNestedScrollingEnabled(false);
        rcvMercancia.setLayoutManager(new GridLayoutManager(getContext(), 2));
        rcvMercancia.setItemAnimator(new DefaultItemAnimator());
        rcvMercancia.setHasFixedSize(true);
        Adapters.MercanciaAdapter adapterc = new MercanciaAdapter(context, listaMercancia);
        rcvMercancia.setAdapter(adapterc);
        Log.v("ProductoFragment", String.valueOf(listaMercancia.size()));
        progressHome.setVisibility(GONE);

    }

    private class productos extends AsyncTask<String, String, String> {
        private ProgressDialog progressDialog = new ProgressDialog(context);
        @Override
        protected void onPreExecute() {
            progressHome  .setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... params) {
            Log.v("ProductosFragment","Inicio de Metodo.");
            String result = "";
            JSONObject jsonO = null;
            JSONArray JSONOarray = null;
            JSONObject jsonData = null;
            MetodosConexion metodosc = new MetodosConexion();
            MetodosJSON metodJ = new MetodosJSON();
            /// ArrayList<Cabecera> cabecera = new ArrayList<Cabecera>();
            transaccion = params[0];
            tipo = params[1];
            ///cabecera.add(new Cabecera("usuario",usuario));
            //cabecera.add(new Cabecera("clave",clave));
            //String token = access_token;
            String metodo = constantesUrl.getBaseURL();
            String metodo2 = constantesUrl.getProductos();

                    try {

                        //jsonO = metodosc.getJSONObject(constantesUrl.getBaseURL() +  constantesUrl.getProductos(), "POST","{}", "",jsonDatos(transaccion,tipo).toString(),"");
                        jsonO = httpsProducto();
                        Log.v("jsonInicio", String.valueOf(jsonO));

                        if (jsonO != null) {

                            JSONArray data = new JSONArray(jsonO.getString("data"));
                            //  existeError = jsonObectInformacion.getString("existeError");
                            listaMercancia =metodJ.jsonProductos(data);



                    result = "true";

                }else{
                    result = "";
                }

                return result;
            } catch (JSONException e) {
                return ""; }
            catch (Exception e) {
                return ""; }
        }
        @Override
        protected void onPostExecute(String respuesta) {
            progressHome.setVisibility(GONE);
            super.onPostExecute(respuesta);
            // cargarListaProducto();
            Log.v("okResult", "ok");
            Log.v("okResult", "ok");

            if(respuesta.equals("true")) {
                cargarProductos();

            }else if(respuesta.equals("true")){



            }
            else{

            }
        }
    }

}