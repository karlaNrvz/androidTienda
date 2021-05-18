package Adapter;

import android.app.Activity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.viamatica.tiendaaplication.Fragments.DetalleFragment;
import com.viamatica.tiendaaplication.R;
import com.viamatica.tiendaaplication.metodoConexion.BDHelper;
import com.viamatica.tiendaaplication.modelos.ProductoVentas;
import com.viamatica.tiendaaplication.modelos.Productos;

import java.util.ArrayList;


public class CarroAdapter  extends RecyclerView.Adapter<CarroAdapter.ItemRowHolder> implements View.OnClickListener {
    private Context mContext;
    private ArrayList<ProductoVentas> dataList;
    private Activity ctx;
    int cantidad =1 ;
    BDHelper admin;

    public CarroAdapter(Context context, ArrayList<ProductoVentas> dataList) {
        this.dataList = dataList;
        this.mContext = context;
        this.ctx = ctx;

    }

    @Override
    public CarroAdapter.ItemRowHolder onCreateViewHolder(ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.carro_adapter, parent, false);
        admin = new BDHelper(mContext, "BDTienda.db", null, 1);
        return new ItemRowHolder(v);
    }

    @Override
    public void onBindViewHolder(final CarroAdapter.ItemRowHolder itemRowHolder, final int position) {
        itemRowHolder.setIsRecyclable(false);
        final ProductoVentas lisData = dataList.get(position);

        itemRowHolder.textCategoria.setText( lisData.getDescripcion());
        itemRowHolder.textprecio.setText( "USD$" +lisData.getPrecio());


        Picasso.get()
                .load(lisData.getImagen())
                .fit()//Para q la imagen ocupe el campo asignado
                .memoryPolicy(MemoryPolicy.NO_STORE)
                .placeholder(R.mipmap.no_hay_imagen)
                .networkPolicy(NetworkPolicy.NO_CACHE)
                .into(itemRowHolder.imgProducto);

        itemRowHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Bundle bundle = new Bundle();
////                    bundle.putSerializable("productos", lisData.getListaProductos());
//                bundle.putSerializable("id_categoria", lisData);
//                //presentar productos
//                AppCompatActivity activity = (AppCompatActivity) v.getContext();
//                DetalleFragment myFragment = new DetalleFragment();
//                activity.getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, myFragment).addToBackStack("SubCategoriasFragment").commit();
//                myFragment.setArguments(bundle);

//
            }

        });
        itemRowHolder.masCantidad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cantidad++;
                if(cantidad<1){
                    cantidad = 1;
                }
                admin.AddProductoCarrito(admin.getWritableDatabase(),lisData,cantidad);
                itemRowHolder.cantidadProd.setText(String.valueOf(cantidad));

            }

        });
        itemRowHolder.menosCantidad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                cantidad--;
                if(cantidad<1){
                    cantidad = 1;
                }
                admin.AddProductoCarrito(admin.getWritableDatabase(),lisData,cantidad);
                itemRowHolder.cantidadProd.setText(String.valueOf(cantidad));

            }

        });
    }


    @Override
    public int getItemCount() {
        if (dataList == null) {
            return 0;
        }
        return dataList.size() ;
    }


    @Override
    public void onClick(View v) {
    }

    public class ItemRowHolder extends RecyclerView.ViewHolder {
        ImageView imgProducto,menosCantidad,masCantidad;
        TextView textCategoria,textprecio;
        EditText cantidadProd;

        public ItemRowHolder(View view) {
            super(view);
            imgProducto = view.findViewById(R.id.imgCompra);
            masCantidad = view.findViewById(R.id.masCantidad);
            menosCantidad = view.findViewById(R.id.menosCantidad);
            textCategoria = view.findViewById(R.id.nombreProductoCompra);
            textprecio = view.findViewById(R.id.textprecio);
            cantidadProd = (EditText) view.findViewById(R.id.cantidadProd);
            cantidadProd.setEnabled(false);
            cantidadProd.setText(String.valueOf(cantidad));

        }


    }

    public interface OnCardClickListner {
        void OnCardClicked(View view, int position);
    }

    public ArrayList<ProductoVentas> dataCheck() {
        return dataList;
    }

    public ProductoVentas getProduct(int position) {
        return dataList.get(position);
    }
}
