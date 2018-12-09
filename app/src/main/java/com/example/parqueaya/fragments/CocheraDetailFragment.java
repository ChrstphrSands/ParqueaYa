package com.example.parqueaya.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.example.parqueaya.R;
import com.example.parqueaya.activities.LoginActivity;
import com.example.parqueaya.api.ParkingApi;
import com.example.parqueaya.api.RetrofitInstance;
import com.example.parqueaya.models.Cliente;
import com.example.parqueaya.models.Cochera;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CocheraDetailFragment extends Fragment {

    private Cochera cochera;

    private TextView cochera_nombre;
    private TextView cochera_espacios;
    private TextView cochera_telefono;
    private TextView cochera_direccion;
    private TextView cochera_descripcion;
    private AppCompatButton reservar;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener authStateListener;

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
        authFirebase();

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
                FirebaseUser firebaseUser = mAuth.getCurrentUser();
                if (firebaseUser != null) {
                    ReservaFragment reservaFragment = new ReservaFragment();

                    Bundle args = new Bundle();
                    args.putSerializable("cochera", cochera);
                    reservaFragment.setArguments(args);

                    getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, reservaFragment)
                        .addToBackStack(null)
                        .commit();
                } else {
                    Toast.makeText(getContext(), "Debe estar registrado", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getContext(), LoginActivity.class);
                    startActivity(intent);
                }
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

    private void authFirebase() {
        mAuth = FirebaseAuth.getInstance();
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

            }
        };
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(authStateListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        mAuth.removeAuthStateListener(authStateListener);
    }
}
