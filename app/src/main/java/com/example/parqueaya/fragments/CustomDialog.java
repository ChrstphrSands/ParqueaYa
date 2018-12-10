package com.example.parqueaya.fragments;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatRatingBar;
import android.view.*;
import android.widget.EditText;
import android.widget.TextView;
import com.example.parqueaya.MyApplication;
import com.example.parqueaya.R;
import com.example.parqueaya.activities.MainActivity;
import com.example.parqueaya.models.Calificacion;
import io.reactivex.annotations.Nullable;

public class CustomDialog extends DialogFragment {

    private TextView nombre;
    private AppCompatRatingBar ratingBar;
    private EditText cochera_comentario;
    private AppCompatButton cancelar, enviar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstance){
        View view = inflater.inflate(R.layout.dialog_add_layout, container, false);

        nombre = view.findViewById(R.id.cliente_nombre);
        cancelar = view.findViewById(R.id.bt_cancelar);
        enviar = view.findViewById(R.id.bt_enviar);
        cochera_comentario = view.findViewById(R.id.cochera_comentario);
        ratingBar = view.findViewById(R.id.rating_bar);


        String mNombre = ((MyApplication) getActivity().getApplicationContext()).getNombre();

        nombre.setText(String.valueOf(mNombre));

        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int cliente_id = ((MyApplication) getActivity().getApplicationContext()).getCliente_id();
                int cochera_id = ((MyApplication) getActivity().getApplicationContext()).getCochera_id();

                String comentario = cochera_comentario.getText().toString();
                float puntaje = ratingBar.getRating();
                if (!comentario.equals("")){
                    Calificacion calificacion = new Calificacion();
                    calificacion.setCliente_id(cliente_id);
                    calificacion.setCochera_id(cochera_id);
                    calificacion.setComentario(comentario);
                    calificacion.setPuntaje(puntaje);

                    MainActivity.reservaRoomDatabase.calificacionDao().insertCalificacion(calificacion);

                }
                getDialog().dismiss();
            }
        });

        return view;
    }

}