package com.viamatica.tiendaaplication.ui.notifications;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.viamatica.tiendaaplication.InicioSesionActivity;
import com.viamatica.tiendaaplication.R;
import com.viamatica.tiendaaplication.metodoConexion.BDHelper;
import com.viamatica.tiendaaplication.modelos.ProductoVentas;
import com.viamatica.tiendaaplication.modelos.Productos;

public class NotificationsFragment extends Fragment {
    SharedPreferences sharedP;
    private NotificationsViewModel notificationsViewModel;
    Button btnCerrarSesion;
    BDHelper admin;
    ProductoVentas prod;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);

        return root;
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        inicializarComponentes(view);
    }

    public void inicializarComponentes(View view){
        admin = new BDHelper(getContext(), "BDTienda.db", null, 1);

        btnCerrarSesion= (Button) view.findViewById(R.id.btnCerrarSesion);
        btnCerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });
    }


    public void logout() {
        admin.deleteAllCarrito(admin.getWritableDatabase());

        SharedPreferences settings = getActivity().getSharedPreferences("Login", Context.MODE_PRIVATE);
        settings.edit().clear().commit();
        Intent intent = new Intent(getActivity().getApplicationContext(), InicioSesionActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

    }
}