package com.example.parqueaya.fragments;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;
import com.example.parqueaya.MyApplication;
import com.example.parqueaya.R;
import com.example.parqueaya.activities.LoginActivity;
import com.example.parqueaya.api.ParkingApi;
import com.example.parqueaya.api.RetrofitInstance;
import com.example.parqueaya.models.Cliente;
import com.example.parqueaya.models.Reserva;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.zxing.WriterException;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReservaDetailFragment extends Fragment {


    public ReservaDetailFragment() {
        // Required empty public constructor
    }

    private TextView detalleFecha;
    private TextView detallePlaca;
    private TextView detalleCosto;
    private TextView detalleEstado;
    private TextView detalleCliente;
    private ImageView detalleQr;

    private Reserva reserva;
    private String fecha;
    private String placa;
    private String nombre;

    Bitmap bitmap;
    QRGEncoder qrgEncoder;

    Cliente cliente;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener authStateListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reserva_detail, container, false);

        authFirebase();
        initComponent(view);
        getCliente();
        setData();
        return view;
    }

    private void authFirebase() {

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                if (firebaseUser != null) {

                } else {

                }
            }
        };

        mAuth = FirebaseAuth.getInstance();
    }

    private void initComponent(View view) {
        detalleFecha = view.findViewById(R.id.detalle_reserva_fecha);
        detallePlaca = view.findViewById(R.id.detalle_reserva_placa);
        detalleCosto = view.findViewById(R.id.detalle_reserva_costo);
        detalleEstado = view.findViewById(R.id.detalle_reserva_estado);
        detalleCliente = view.findViewById(R.id.detalle_reserva_cliente);
        detalleQr = view.findViewById(R.id.detalle_reserva_qr);
    }

    private void setData() {

        String nombre = ((MyApplication) this.getActivity().getApplicationContext()).getNombre();
        String placa = ((MyApplication) this.getActivity().getApplicationContext()).getPlaca();
        String fecha = ((MyApplication) this.getActivity().getApplicationContext()).getFecha();
        String costo = String.valueOf(((MyApplication) this.getActivity().getApplicationContext()).getPrecio_aproximado());

        detalleFecha.setText(fecha);
        detallePlaca.setText(placa);
        detalleCosto.setText(costo);
//        detalleEstado.setText(String.valueOf(reserva.getReservaEstadoId()));
        detalleCliente.setText(nombre);
        generateQr();
    }

    private void generateQr() {
        qrgEncoder = new QRGEncoder("Hola", null, QRGContents.Type.TEXT, 150);

        try {
            bitmap = qrgEncoder.encodeAsBitmap();
            detalleQr.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }

    private void getCliente() {
        ParkingApi parkingApi = RetrofitInstance.createService(ParkingApi.class);
        FirebaseUser firebaseUser = mAuth.getCurrentUser();
        String UID  = firebaseUser.getUid();
        Call<Cliente> callCliente = parkingApi.getClienteDetail(UID);

        callCliente.enqueue(new Callback<Cliente>() {
            @Override
            public void onResponse(Call<Cliente> call, Response<Cliente> response) {
                if (response.isSuccessful() && response.code() == 200) {
                    cliente = response.body();
                }
            }

            @Override
            public void onFailure(Call<Cliente> call, Throwable t) {
                Log.e("Error cliente", t.getLocalizedMessage());
            }
        });
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
