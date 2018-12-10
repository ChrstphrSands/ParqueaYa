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
import com.bumptech.glide.Glide;
import com.example.parqueaya.R;
import com.example.parqueaya.models.Historial;

import java.util.List;

public class HistorialAdapter extends RecyclerView.Adapter<HistorialAdapter.ItemViewHolder>{

    private static List<Historial> dataList;
    private LayoutInflater mInflater;
    private Context context;
    private HistorialClickListener clicklistener = null;
    private final String URL_FOTO = "https://sistemaparqueo.azurewebsites.net/Uploads/Cocheras/";

    public HistorialAdapter(Context ctx, List<Historial> data) {
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
        private TextView favorito_id;

        public ItemViewHolder(final View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);

            image = itemView.findViewById(R.id.imageMain);
            favorito_nombre = itemView.findViewById(R.id.favorito_nombre);
            favorito_direccion = itemView.findViewById(R.id.favorito_direccion);
            favorito_precio = itemView.findViewById(R.id.favorito_costo);
            eliminar = itemView.findViewById(R.id.favorito_eliminar);
            favorito_id = itemView.findViewById(R.id.favorito_id);

            //            eliminar.setOnClickListener(new View.OnClickListener() {
            //                @Override
            //                public void onClick(View v) {
            //                    int pos = getAdapterPosition() + 1;
            //                    Favorito favorito = new Favorito();
            //                    int id = favorito_id.getId();
            //
            //                    favorito.setId(id);
            //
            //                    Toast.makeText(context, "Button Shopping Cart " + pos + " clicked!" + favorito_id, Toast.LENGTH_SHORT).show();
            //                    MainActivity.reservaRoomDatabase.favoritoDao().delete(favorito);
            //                }
            //            });
        }

        @Override
        public void onClick(View v) {

        }
    }

    public void setClickListener(HistorialClickListener listener) {
        this.clicklistener = listener;
    }

    @NonNull
    @Override
    public HistorialAdapter.ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_favorito, parent, false);
        HistorialAdapter.ItemViewHolder itemViewHolder = new HistorialAdapter.ItemViewHolder(view);
        return itemViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull HistorialAdapter.ItemViewHolder holder, int position) {
        Glide.with(context)
            .load(URL_FOTO + dataList.get(position).getFoto())
            .thumbnail(0.01f)
            .centerCrop()
            .into(holder.image);
        holder.favorito_nombre.setText(dataList.get(position).getNombre());
        holder.favorito_precio.setText(dataList.get(position).getPrecio());
        holder.favorito_direccion.setText(dataList.get(position).getDireccion());
        holder.favorito_id.setTextColor(dataList.get(position).getId());

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

}
