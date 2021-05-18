package com.viamatica.tiendaaplication.ui.dashboard;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.viamatica.tiendaaplication.Fragments.DetalleFragment;
import com.viamatica.tiendaaplication.Fragments.MapaFragment;
import com.viamatica.tiendaaplication.R;
import com.viamatica.tiendaaplication.metodoConexion.BDHelper;
import com.viamatica.tiendaaplication.modelos.ProductoVentas;
import com.viamatica.tiendaaplication.modelos.Productos;

import java.util.ArrayList;

import static android.view.View.GONE;

public class DashboardFragment extends Fragment {
    ConstraintLayout vacioCarro ;
    private DashboardViewModel dashboardViewModel;
    Button btnCComprarCarro ;
    RecyclerView rcvMerca;
    Context context;
    BDHelper admin;
    ArrayList<ProductoVentas> listaMer = new ArrayList<>();
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);

        return root;
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        inicializarComponentes(view);
    }

    public void inicializarComponentes(View view){
        context = getActivity();
        rcvMerca =(RecyclerView) view.findViewById(R.id.rcvMerca);
        btnCComprarCarro =(Button) view.findViewById(R.id.btnComprarCarro) ;
        vacioCarro =(ConstraintLayout) view.findViewById(R.id.vacioCarro);
        admin = new BDHelper(getContext(), "BDTienda.db", null, 1);
        SQLiteDatabase db = admin.getReadableDatabase();
        listaMer = admin.getCarrito(db);
         cargarProductos();
         btnCComprarCarro.setOnClickListener(new View.OnClickListener() {
                                                 @Override
                                                 public void onClick(View v) {
                                                     MapaFragment mapa = new MapaFragment();
                                                     ChangeFragment(mapa);
                                                 }
                                             }
         );

    }
    private void ChangeFragment(Fragment fragment){
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.nav_host_fragment, fragment);
        fragmentTransaction.commit();

    }

    public void cargarProductos(){

        if(listaMer.size()<=0){

            vacioCarro.setVisibility(View.VISIBLE);
            btnCComprarCarro.setVisibility(GONE);

        }else{
            btnCComprarCarro.setVisibility(View.VISIBLE);

            vacioCarro.setVisibility(GONE);
        rcvMerca.setNestedScrollingEnabled(false);
        rcvMerca.setLayoutManager(new LinearLayoutManager(context));
        rcvMerca.setItemAnimator(new DefaultItemAnimator());
        rcvMerca.setHasFixedSize(true);
        Adapter.CarroAdapter adapterc = new Adapter.CarroAdapter(context, listaMer);
        rcvMerca.setAdapter(adapterc);
        Log.v("ProductoFragment", String.valueOf(listaMer.size()));

    }}




}