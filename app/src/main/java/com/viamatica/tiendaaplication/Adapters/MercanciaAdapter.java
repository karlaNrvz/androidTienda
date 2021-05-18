package Adapters;

import android.app.Activity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.viamatica.tiendaaplication.Fragments.DetalleFragment;
import com.viamatica.tiendaaplication.R;
import com.viamatica.tiendaaplication.modelos.Productos;

import java.util.ArrayList;


public class MercanciaAdapter  extends RecyclerView.Adapter<MercanciaAdapter.ItemRowHolder> implements View.OnClickListener {
    private Context mContext;
    private ArrayList<Productos> dataList;
    private Activity ctx;

    public MercanciaAdapter(Context context, ArrayList<Productos> dataList) {
        this.dataList = dataList;
        this.mContext = context;
        this.ctx = ctx;
    }

    @Override
    public MercanciaAdapter.ItemRowHolder onCreateViewHolder(ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.mercancia_adapter, parent, false);

        return new ItemRowHolder(v);
    }

    @Override
    public void onBindViewHolder(final MercanciaAdapter.ItemRowHolder itemRowHolder, final int position) {
        itemRowHolder.setIsRecyclable(false);
        final Productos lisData = dataList.get(position);

        itemRowHolder.textCategoria.setText(lisData.getDescripcion());
        itemRowHolder.textPrecio.setText("USD$"+lisData.getPrecio());


            Picasso.get()
                    .load("/Users/viamatica/TiendaAplication/app/src/main/res/drawable/"+lisData.getImagen())
                    .fit()//Para q la imagen ocupe el campo asignado
                    .memoryPolicy(MemoryPolicy.NO_STORE)
                    .placeholder(R.mipmap.no_hay_imagen)
                    .networkPolicy(NetworkPolicy.NO_CACHE)
                    .into(itemRowHolder.imgProducto);

        itemRowHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               Bundle bundle = new Bundle();
//                    bundle.putSerializable("productos", lisData.getListaProductos());
                bundle.putSerializable("id_categoria", lisData);
                //presentar productos
             AppCompatActivity activity = (AppCompatActivity) v.getContext();
               DetalleFragment myFragment = new DetalleFragment();
               activity.getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, myFragment).addToBackStack("SubCategoriasFragment").commit();
                 myFragment.setArguments(bundle);

//                    final Dialog dialog = new Dialog(ctx);
//                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//                    dialog.setContentView(R.layout.fragment_sub_categorias);
//                    dialog.show();
//
//
//
//                    final Dialog MyDialog = new Dialog(ctx);
//                    MyDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);//quita titulo a los cuadros de dialogos
//                    MyDialog.setContentView(R.layout.dialog_general);
//                    MyDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//                    MyDialog.getWindow().getAttributes().windowAnimations = R.style.CustomDialog;
//                    MyDialog.setCancelable(false);
//                    Button btnContinuar = MyDialog.findViewById(R.id.btnContinuar);
//                    TextView txtMensaje = MyDialog.findViewById(R.id.txtmensaje);
//
//                    txtMensaje.setText("No tiene productos disponibles");
//
//                    btnContinuar.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            MyDialog.dismiss();
//
//                        }
//                    });
//
//
//
//                    MyDialog.show();

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
        ImageView imgProducto;
        TextView textCategoria,textPrecio;

        public ItemRowHolder(View view) {
            super(view);
            imgProducto = view.findViewById(R.id.imgSubCat);
            textCategoria = view.findViewById(R.id.textCategoria);
            textPrecio = view.findViewById(R.id.textPrecio);
        }


    }

    public interface OnCardClickListner {
        void OnCardClicked(View view, int position);
    }

    public ArrayList<Productos> dataCheck() {
        return dataList;
    }

    public Productos getProduct(int position) {
        return dataList.get(position);
    }
}
