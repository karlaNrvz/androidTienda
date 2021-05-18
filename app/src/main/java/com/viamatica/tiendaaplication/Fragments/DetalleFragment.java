package com.viamatica.tiendaaplication.Fragments;

import android.database.sqlite.SQLiteDatabase;
import android.media.Image;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.viamatica.tiendaaplication.R;
import com.viamatica.tiendaaplication.metodoConexion.BDHelper;
import com.viamatica.tiendaaplication.modelos.Productos;
import com.viamatica.tiendaaplication.ui.dashboard.DashboardFragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DetalleFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetalleFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    ImageView imgProducto;
    TextView descripcion,precio;
    Button btnCarro;
    BDHelper admin ;
    int cantidad =1;
    Productos productosInfo;
    public DetalleFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DetalleFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DetalleFragment newInstance(String param1, String param2) {
        DetalleFragment fragment = new DetalleFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            productosInfo =(Productos) getArguments().getSerializable("id_categoria");

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detalle, container, false);
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        inicializarComponentes(view);
    }

    public void inicializarComponentes(View view){
        admin = new BDHelper(getContext(), "BDTienda.db", null, 1);
        imgProducto =(ImageView) view.findViewById(R.id.imgDetalle);
        descripcion =(TextView) view.findViewById(R.id.descripcion);
        precio =(TextView) view.findViewById(R.id.precio);
        btnCarro = (Button) view.findViewById(R.id.btnCarro);

        btnCarro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = admin.getWritableDatabase();
                if(admin.AddProductoCarrito(db,productosInfo,cantidad)){
                    Log.v("AddProductoCarrito","Se agrego al carrito");

                    Toast toast = Toast.makeText(getContext(), "Usted ha agregado un nuevo producto al carrito.", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    DashboardFragment det= new DashboardFragment();
                    ChangeFragment(det);

                }else{
                    Toast toast = Toast.makeText(getContext(), "No se pudo agregar el producto al carrito, intente de nuevo.", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }


            }
        });
        setearComponente();

    }

    private void ChangeFragment(Fragment fragment){
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.nav_host_fragment, fragment);
        fragmentTransaction.commit();

    }

    public void setearComponente(){
        descripcion.setText(productosInfo.getDetalle());
        precio.setText("USD$" +productosInfo.getPrecio());
        Picasso.get()
                .load(productosInfo.getImagen())
                .fit()//Para q la imagen ocupe el campo asignado
                .memoryPolicy(MemoryPolicy.NO_STORE)
                .placeholder(R.mipmap.no_hay_imagen)
                .networkPolicy(NetworkPolicy.NO_CACHE)
                .into(imgProducto);


    }


}