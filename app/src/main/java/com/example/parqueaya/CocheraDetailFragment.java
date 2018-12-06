package com.example.parqueaya;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.parqueaya.models.Cochera;

public class CocheraDetailFragment extends Fragment {

    private Cochera cochera;

    private TextView cochera_nombre;
    private TextView cochera_espacios;
    private TextView cochera_telefono;
    private TextView cochera_direccion;
    private TextView cochera_descripcion;
    private AppCompatButton reservar;


    public CocheraDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cochera_detail, container, false);

        assert getArguments() != null;
        cochera = (Cochera) getArguments().getSerializable("cochera");
        Log.d("Cochera", String.valueOf(cochera));

        initComponents(view);
        setDataComponents();

        return view;
    }

    private void initComponents(View view) {

        reservar = view.findViewById(R.id.reservar);
        cochera_nombre = view.findViewById(R.id.cochera_nombre);
        cochera_espacios = view.findViewById(R.id.cochera_espacios);
        cochera_telefono = view.findViewById(R.id.cochera_telefono);
        cochera_direccion = view.findViewById(R.id.cochera_direccion);
        cochera_descripcion = view.findViewById(R.id.cochera_descripcion);

        reservar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReservaFragment reservaFragment = new ReservaFragment();

                Bundle args = new Bundle();
                args.putSerializable("cochera", cochera);
                reservaFragment.setArguments(args);

                getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, reservaFragment)
                    .addToBackStack(null)
                    .commit();
            }
        });
    }

    private void setDataComponents() {
        cochera_nombre.setText(cochera.getNombre());
        cochera_espacios.setText(cochera.getCodigoPostal());
        cochera_telefono.setText(cochera.getTelefono());
        cochera_direccion.setText(cochera.getDireccion());
        cochera_descripcion.setText(cochera.getDescripcion());
    }
}
