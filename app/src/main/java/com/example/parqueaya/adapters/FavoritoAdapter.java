package com.example.parqueaya.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.example.parqueaya.FavoritoClickListener;
import com.example.parqueaya.R;
import com.example.parqueaya.models.Favorito;

import java.net.URL;
import java.util.List;

public class FavoritoAdapter extends RecyclerView.Adapter<FavoritoAdapter.ItemViewHolder> {

    private static List<Favorito> dataList;
    private LayoutInflater mInflater;
    private Context context;
    private FavoritoClickListener clicklistener = null;
    private final String URL_FOTO = "https://sistemaparqueo.azurewebsites.net/Uploads/Cocheras/";

    public FavoritoAdapter(Context ctx, List<Favorito> data) {
        context = ctx;
        dataList = data;
        mInflater = LayoutInflater.from(context);
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView image;
        private TextView favorito_nombre;
        private TextView favorito_direccion;
        private TextView favorito_precio;
        private CardView eliminar;

        public ItemViewHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);

            image = itemView.findViewById(R.id.imageMain);
            favorito_nombre = itemView.findViewById(R.id.favorito_nombre);
            favorito_direccion = itemView.findViewById(R.id.favorito_direccion);
            favorito_precio = itemView.findViewById(R.id.favorito_costo);

            eliminar = itemView.findViewById(R.id.favorito_eliminar);

            eliminar.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.favorito_eliminar:
                    int pos2 = getAdapterPosition() + 1;
                    Toast.makeText(context, "Button Love " + pos2 + " clicked!", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    if (clicklistener != null) {
                        clicklistener.itemClicked(v, getAdapterPosition());
                    }
                    break;
            }
        }
    }

    public void setClickListener(FavoritoClickListener listener) {
        this.clicklistener = listener;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_favorito, parent, false);
        ItemViewHolder itemViewHolder = new ItemViewHolder(view);
        return itemViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        Glide.with(context)
            .load(URL_FOTO + dataList.get(position).getFoto())
            .thumbnail(0.01f)
            .centerCrop()
            .into(holder.image);
        holder.favorito_nombre.setText(dataList.get(position).getNombre());
        holder.favorito_precio.setText(dataList.get(position).getPrecio());
        holder.favorito_direccion.setText(dataList.get(position).getDireccion());
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
}
