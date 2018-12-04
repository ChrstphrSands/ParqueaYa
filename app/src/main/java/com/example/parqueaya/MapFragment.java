package com.example.parqueaya;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class MapFragment extends Fragment {

    private Button mostrar;

    public MapFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        mostrar = view.findViewById(R.id.mostrar);
        showDetalle();
        return view;
    }

    private void showDetalle() {
        mostrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CocheraDetalleFragment cocheraDetalleFragment = new CocheraDetalleFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, cocheraDetalleFragment)
                    .addToBackStack(null)
                    .commit();
            }
        });
    }
}
