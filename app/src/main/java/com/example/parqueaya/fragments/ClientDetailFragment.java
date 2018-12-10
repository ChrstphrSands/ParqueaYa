package com.example.parqueaya.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.example.parqueaya.R;
import com.example.parqueaya.api.ParkingApi;
import com.example.parqueaya.api.RetrofitInstance;
import com.example.parqueaya.models.Cliente;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.mikhaellopez.circularimageview.CircularImageView;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClientDetailFragment extends Fragment {

    private FloatingActionButton actionButton;

    public ClientDetailFragment() {
        // Required empty public constructor
    }

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener authStateListener;

    private ParkingApi parkingApi;

    private Cliente cliente;

    private TextView titleNombre;
    private TextView titleEmail;
    private TextView clienteNombre;
    private TextView clienteApellidos;
    private TextView clienteDNI;
    private TextView clienteCelular;
    private TextView clienteTelefono;
    private TextView clienteDireccion;
    private CircularImageView clienteFoto;
    private String urlFoto;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_client_detail, container, false);
        actionButton = view.findViewById(R.id.cliente_detail_editar);
        initComponents(view);
        onClick();
        authFirebase();

        return view;
    }

    private void initComponents(View view) {
        titleNombre = view.findViewById(R.id.title_nombre);
        titleEmail = view.findViewById(R.id.title_email);
        clienteNombre = view.findViewById(R.id.client_detail_nombre);
        clienteApellidos = view.findViewById(R.id.client_detail_apellidos);
        clienteDNI = view.findViewById(R.id.client_detail_dni);
        clienteCelular = view.findViewById(R.id.client_detail_celular);
        clienteTelefono = view.findViewById(R.id.client_detail_telefono);
        clienteDireccion = view.findViewById(R.id.cliente_detail_direccion);
        clienteFoto = view.findViewById(R.id.cliente_detail_foto);
    }

    private void authFirebase() {
        mAuth = FirebaseAuth.getInstance();
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                final FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                if (firebaseUser != null) {
                    parkingApi = RetrofitInstance.createService(ParkingApi.class);

                    Call<Cliente> call = parkingApi.getClienteDetail(firebaseUser.getUid());

                    call.enqueue(new Callback<Cliente>() {
                        @Override
                        public void onResponse(Call<Cliente> call, Response<Cliente> response) {
                            if (response.isSuccessful() && response.code() == 200) {
                                cliente = response.body();
                                assert cliente != null;
                                setData(cliente);
                                Glide.with(getActivity())
                                    .load(firebaseUser.getPhotoUrl())
                                    .centerCrop()
                                    .into(clienteFoto);
                                urlFoto = String.valueOf(firebaseUser.getPhotoUrl());
                            }
                        }

                        @Override
                        public void onFailure(Call<Cliente> call, Throwable t) {
                            Log.e("Error cliente", t.getLocalizedMessage());
                        }
                    });

                } else {
                    Toast.makeText(getContext(), "Usuario no logueado", Toast.LENGTH_SHORT).show();
                }
            }
        };
    }

    private void setData(Cliente cliente) {
        titleNombre.setText(cliente.getNombre());
        if (cliente.getEmail() == null) {
            titleEmail.setText("Desconocido");
        } else {
            titleEmail.setText(cliente.getEmail());
        }

        clienteNombre.setText(cliente.getNombre());
        clienteApellidos.setText(cliente.getApellido());
        clienteDNI.setText(String.valueOf(cliente.getDNI()));
        clienteCelular.setText(String.valueOf(cliente.getCelular()));
        clienteTelefono.setText(String.valueOf(cliente.getTelefono()));
        clienteDireccion.setText(cliente.getDireccion());
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

    private void onClick() {
        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClientEdiFragment clientEditFragment = new ClientEdiFragment();
                Bundle bundle = new Bundle();
                bundle.putSerializable("cliente", cliente);
                bundle.putString("foto", urlFoto);
                clientEditFragment.setArguments(bundle);
                getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, clientEditFragment)
                    .addToBackStack(null)
                    .commit();
            }
        });
    }


}
