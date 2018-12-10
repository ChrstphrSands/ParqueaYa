package com.example.parqueaya.fragments;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.example.parqueaya.R;
import com.example.parqueaya.models.Cliente;
import com.example.parqueaya.models.Vehiculo;
import com.example.parqueaya.services.DataService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ClientEdiFragment extends Fragment implements View.OnClickListener {

    private Cliente cliente;

    private EditText cliente_nombre;
    private EditText cliente_apellido;
    private EditText cliente_dni;
    private EditText cliente_email;
    private EditText cliente_celular;
    private EditText cliente_telefono;
    private EditText cliente_direccion;
    private FloatingActionButton guardar;
    private ImageView cliente_foto;
    private EditText cliente_vehiculos;

    private String uid;
    private String urlFoto;

    public ClientEdiFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_client_edit, container, false);

        cliente = (Cliente) getArguments().getSerializable("cliente");
        urlFoto = getArguments().getString("foto");
        uid = cliente.getUID();
        initComponents(view);
        setData();
        return view;
    }

    private void initComponents(View view) {
        cliente_nombre = view.findViewById(R.id.cliente_nombre);
        cliente_apellido = view.findViewById(R.id.cliente_apellidos);
        cliente_dni = view.findViewById(R.id.cliente_dni);
        cliente_email = view.findViewById(R.id.cliente_email);
        cliente_celular = view.findViewById(R.id.cliente_celular);
        cliente_telefono = view.findViewById(R.id.cliente_telefono);
        cliente_direccion = view.findViewById(R.id.cliente_direccion);
        cliente_foto = view.findViewById(R.id.cliente_foto);
        cliente_vehiculos = view.findViewById(R.id.cliente_vehiculos);
        guardar = view.findViewById(R.id.cliente_guardar);

        guardar.setOnClickListener(this);
    }

    private void setData() {
        cliente_nombre.setText(cliente.getNombre());
        cliente_apellido.setText(cliente.getApellido());
        cliente_dni.setText(String.valueOf(cliente.getDNI()));
        cliente_email.setText(cliente.getEmail());
        cliente_celular.setText(String.valueOf(cliente.getCelular()));
        cliente_telefono.setText(String.valueOf(cliente.getTelefono()));
        cliente_direccion.setText(cliente.getDireccion());
        Glide.with(getContext())
            //        BuildConfig.IMAGE_URL + dataList.get(position).getImageUrl()
            .load(urlFoto)
            .centerCrop()
            .into(cliente_foto);

        StringBuffer result = new StringBuffer();
        for (int i = 0; i < cliente.getVehiculos().size(); i++) {
            result.append(cliente.getVehiculos().get(i).getPlaca()+ ", ");
        }
        String vehiculos = result.toString().trim();

        if (vehiculos.endsWith(",")) {
            vehiculos = vehiculos.substring(0, vehiculos.length() - 1);
        }

        cliente_vehiculos.setText(vehiculos);
    }

    private void saveData() {

        String nombre = String.valueOf(cliente_nombre.getText());
        String apellido = String.valueOf(cliente_apellido.getText());
        int dni = Integer.parseInt(String.valueOf(cliente_dni.getText()));
        String email = String.valueOf(cliente_email.getText());
        int celular = Integer.parseInt(String.valueOf(cliente_celular.getText()));
        int telefono = Integer.parseInt(String.valueOf(cliente_telefono.getText()));
        String direccion = String.valueOf(cliente_direccion.getText());
        List<Vehiculo> vehiculos = new ArrayList<>();

        String placas = String.valueOf(cliente_vehiculos.getText());
        Log.d("Vehiculoss", placas);

        saveVehiculos(placas);

        cliente = new Cliente(cliente.getCliente_id(), apellido, direccion, celular, telefono, nombre, dni, email, uid);
        Log.d("Cliente", String.valueOf(cliente));

        DataService.getInstance().updateCliente(cliente.getCliente_id(), cliente);
    }

    private void saveVehiculos(String vehiculos) {
        List<String> list = new ArrayList<>(Arrays.asList(vehiculos.split(", ")));

        Vehiculo vehiculo;

        for (int i = 0; i < list.size(); i++) {
            vehiculo = new Vehiculo(list.get(i), 4, cliente.getCliente_id());
            DataService.getInstance().setVehiculo(vehiculo);
        }


    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.cliente_guardar) {
            saveData();
            ClientDetailFragment clientDetailFragment = new ClientDetailFragment();
            getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, clientDetailFragment)
                .addToBackStack(null)
                .commit();
        }
    }
}