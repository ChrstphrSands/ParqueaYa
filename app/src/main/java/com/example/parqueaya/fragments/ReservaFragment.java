package com.example.parqueaya.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatSpinner;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.example.parqueaya.R;
import com.example.parqueaya.api.ParkingApi;
import com.example.parqueaya.api.RetrofitInstance;
import com.example.parqueaya.models.*;
import com.example.parqueaya.services.DataService;
import com.example.parqueaya.utils.Tools;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ReservaFragment extends Fragment {

    private TextView hora_entrada;
    private TextView hora_salida;
    private TextView cliente_nombre;
    private TextView fecha_reserva;
    private TextView precio_aproximado;
    private TextView tiempo_aproximado;
    private Button enviar_reserva;
    private MaterialBetterSpinner cliente_vehiculos;

    private Cochera cochera;
    private Cliente cliente;
    private Reserva reserva = new Reserva();

    private List<Servicio> servicios;
    private List<Vehiculo> vehiculos;
    private List<String> listVehiculos = new ArrayList<>();

    private long entrada;
    private long salida;

    private int vehiculoId;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener authStateListener;

    private ParkingApi parkingApi;

    public ReservaFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reserva, container, false);

        assert getArguments() != null;
        cochera = (Cochera) getArguments().getSerializable("cochera");
        Log.d("Cochera: ", String.valueOf(cochera));
        initComponents(view);
        setServicio();
        authFirebase(view);

        return view;
    }

    private void initComponents(View view) {
        enviar_reserva = view.findViewById(R.id.guardar);
        fecha_reserva = view.findViewById(R.id.reserva_fecha);
        hora_salida = view.findViewById(R.id.reserva_hora_salida);
        precio_aproximado = view.findViewById(R.id.reserva_precio);
        tiempo_aproximado = view.findViewById(R.id.reserva_tiempo);
        hora_entrada = view.findViewById(R.id.reserva_hora_entrada);
        cliente_nombre = view.findViewById(R.id.reserva_cliente_nombre);
        cliente_nombre = view.findViewById(R.id.reserva_cliente_nombre);
        cliente_vehiculos = view.findViewById(R.id.reserva_cliente_vehiculos);

        hora_entrada.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setEntrada((TextView) v);
            }
        });

        hora_salida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setSalida((TextView) v);
            }
        });

        enviar_reserva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reserva = new Reserva(setFormattedTime(entrada),
                    servicios.get(0).getCosto(), 1, vehiculoId, servicios.get(0).getId(), "",
                    setFormattedTime(salida), "");
                saveData(reserva);
                reserva = null;
            }
        });
    }

    private void authFirebase(final View view) {
        mAuth = FirebaseAuth.getInstance();
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                if (firebaseUser != null) {
                    parkingApi = RetrofitInstance.createService(ParkingApi.class);

                    Call<Cliente> call = parkingApi.getClienteDetail(firebaseUser.getUid());

                    call.enqueue(new Callback<Cliente>() {
                        @Override
                        public void onResponse(Call<Cliente> call, Response<Cliente> response) {
                            if (response.isSuccessful() && response.code() == 200) {
                                cliente = response.body();
                                if (cliente != null) {
                                    cliente_nombre.setText(String.format("%s %s", cliente.getNombre(), cliente.getApellido()));
                                    vehiculos = cliente.getVehiculos();
                                    Log.d("Vehiculo", String.valueOf(vehiculos));
                                    setVehiculos(vehiculos, view);
                                }
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

    private void setServicio() {
        assert cochera != null;
        ParkingApi parkingApi = RetrofitInstance.createService(ParkingApi.class);
        Call<List<Servicio>> call = parkingApi.getServicios(cochera.getCocheraId());

        call.enqueue(new Callback<List<Servicio>>() {
            @Override
            public void onResponse(Call<List<Servicio>> call, Response<List<Servicio>> response) {
                if (response.isSuccessful() && response.code() == 200) {
                    servicios = response.body();
                    Log.d("Servicio", String.valueOf(servicios));
                    Double precio = servicios.get(0).getCosto();

                    precio_aproximado.setText(String.valueOf(precio));
                }
            }

            @Override
            public void onFailure(Call<List<Servicio>> call, Throwable t) {

            }

        });
    }

//    private void getCliente(final View view) {
//        ParkingApi parkingApi = RetrofitInstance.createService(ParkingApi.class);
//        Call<Cliente> call = parkingApi.getCliente(4);
//
//        call.enqueue(new Callback<Cliente>() {
//            @Override
//            public void onResponse(Call<Cliente> call, Response<Cliente> response) {
//                if (response.isSuccessful() && response.code() == 200) {
//                    cliente = response.body();
//                    if (cliente != null) {
//                        cliente_nombre.setText(String.format("%s %s", cliente.getNombre(), cliente.getApellido()));
//                        vehiculos = cliente.getVehiculos();
//                        setVehiculos(vehiculos, view);
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<Cliente> call, Throwable t) {
//
//            }
//
//        });
//    }

    private void setVehiculos(final List<Vehiculo> vehiculos, View view) {
        for (int i = 0; i < vehiculos.size(); i++) {
            listVehiculos.add(String.valueOf(vehiculos.get(i).getPlaca()));
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getContext(), R.layout.support_simple_spinner_dropdown_item,
            listVehiculos);
        MaterialBetterSpinner materialBetterSpinner = view.findViewById(R.id.reserva_cliente_vehiculos);
        materialBetterSpinner.setAdapter(arrayAdapter);

        if (vehiculos.size() > 1) {
            materialBetterSpinner.setHint("Selecciona tu vehiculo");
        } else {
            materialBetterSpinner.setText(vehiculos.get(0).getPlaca());
        }

        materialBetterSpinner.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                vehiculoId = vehiculos.get(position).getId();
            }
        });
    }

    private void setEntrada(final TextView textView) {
        Calendar cur_calender = Calendar.getInstance();
        TimePickerDialog datePicker = TimePickerDialog.newInstance(new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calendar.set(Calendar.MINUTE, minute);
                calendar.set(Calendar.AM_PM, calendar.get(Calendar.AM_PM));
                entrada = calendar.getTimeInMillis();
                textView.setText(Tools.getFormattedTimeEvent(entrada));
            }
        }, cur_calender.get(Calendar.HOUR_OF_DAY), cur_calender.get(Calendar.MINUTE), true);
        datePicker.setThemeDark(false);
        datePicker.setAccentColor(getResources().getColor(R.color.colorPrimary));
        datePicker.show(getActivity().getFragmentManager(), "Timepickerdialog");
    }

    private void setSalida(final TextView textView) {
        Calendar cur_calender = Calendar.getInstance();
        TimePickerDialog datePicker = TimePickerDialog.newInstance(new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calendar.set(Calendar.MINUTE, minute);
                calendar.set(Calendar.AM_PM, calendar.get(Calendar.AM_PM));
                salida = calendar.getTimeInMillis();
                textView.setText(Tools.getFormattedTimeEvent(salida));
            }
        }, cur_calender.get(Calendar.HOUR_OF_DAY), cur_calender.get(Calendar.MINUTE), true);
        datePicker.setThemeDark(false);
        datePicker.setAccentColor(getResources().getColor(R.color.colorPrimary));
        datePicker.show(getActivity().getFragmentManager(), "Timepickerdialog");
    }


    public static String setFormattedTime(Long time) {
        SimpleDateFormat newFormat = new SimpleDateFormat("H:mm:ss");
        return newFormat.format(new Date(time));
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

    private void saveData(Reserva reserva)  {
        DataService.getInstance().setReserva(reserva);
    }
}
