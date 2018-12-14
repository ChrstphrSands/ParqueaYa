package com.example.parqueaya.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
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
        private TextView clienteNombre;
        private TextView nombreCochera;
        private TextView historialFecha;
        private TextView historialPrecio;
        private TextView historialPlaca;
        private TextView historialTiempo;


        public ItemViewHolder(final View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);

//            clienteNombre = itemView.findViewById(R.id.cliente_nombre);
            nombreCochera = itemView.findViewById(R.id.nombre_cochera);
            historialFecha = itemView.findViewById(R.id.historial_fecha);
            historialPrecio = itemView.findViewById(R.id.historial_precio);
            historialPlaca = itemView.findViewById(R.id.historial_placa);
            historialTiempo = itemView.findViewById(R.id.historial_tiempo);

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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_historial, parent, false);
        HistorialAdapter.ItemViewHolder itemViewHolder = new HistorialAdapter.ItemViewHolder(view);
        return itemViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull HistorialAdapter.ItemViewHolder holder, int position) {
        holder.nombreCochera.setText(dataList.get(position).getNombre_cochera());
        holder.historialFecha.setText(dataList.get(position).getFecha_reserva());
        holder.historialPrecio.setText(dataList.get(position).getTotal());
        holder.historialTiempo.setText(dataList.get(position).getTiempo_reserva());
        holder.historialPlaca.setText(dataList.get(position).getPlaca());

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

}
