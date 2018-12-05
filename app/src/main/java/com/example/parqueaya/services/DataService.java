package com.example.parqueaya.services;

import android.util.Log;
import com.example.parqueaya.API.ParkingApi;
import com.example.parqueaya.API.RetrofitInstance;
import com.example.parqueaya.models.Cliente;
import com.example.parqueaya.models.Cochera;
import com.example.parqueaya.models.Reserva;
import com.example.parqueaya.models.Servicio;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;
import java.util.List;

public class DataService {
    private List<Cochera> cocheraList = new ArrayList<>();
    private List<Servicio> servicioList = new ArrayList<>();
    private static DataService instance = new DataService();
    private List<Cliente> clienteList = new ArrayList<>();

    private Cliente cliente = new Cliente();
    RetrofitInstance retrofitInstance = new RetrofitInstance();

    public static DataService getInstance() {
        return instance;
    }

    private DataService() {

    }

    public List<Cochera> getCocheras() {
        //        RetrofitInstance apiHelper = new RetrofitInstance();
        ParkingApi parkingApi = RetrofitInstance.createService(ParkingApi.class);

        Call<List<Cochera>> cocherasArray = parkingApi.getCocheras();
        cocherasArray.enqueue(new Callback<List<Cochera>>() {
            @Override
            public void onResponse(Call<List<Cochera>> call, Response<List<Cochera>> response) {
                if (response.isSuccessful()) {
                    List<Cochera> cocheras = response.body();
                    cocheraList.addAll(cocheras);
                }
            }

            @Override
            public void onFailure(Call<List<Cochera>> call, Throwable t) {
                Log.e("Wiwi", t.getLocalizedMessage());
            }
        });

        return cocheraList;
    }

    public Cliente getClienteDetail(String UID) {
        //        RetrofitInstance retrofitInstance = new RetrofitInstance();
        ParkingApi parkingApi = RetrofitInstance.createService(ParkingApi.class);
        Call<Cliente> callCliente = parkingApi.getClienteDetail(UID);

        callCliente.enqueue(new Callback<Cliente>() {
            @Override
            public void onResponse(Call<Cliente> call, Response<Cliente> response) {
                if (response.isSuccessful() && response.code() == 200) {
                    cliente = response.body();
                    //                    clienteList.addAll(clientes);
                    Log.d("Lista de clientes", String.valueOf(cliente));
                }
            }

            @Override
            public void onFailure(Call<Cliente> call, Throwable t) {
                Log.e("Error cliente", t.getLocalizedMessage());
            }
        });
        return cliente;
    }

    public Cliente getCliente(int ClienteId) {

        //        retrofitInstance = new RetrofitInstance();

        ParkingApi parkingApi = RetrofitInstance.createService(ParkingApi.class);
        Call<Cliente> callCliente = parkingApi.getCliente(ClienteId);

        callCliente.enqueue(new Callback<Cliente>() {
            @Override
            public void onResponse(Call<Cliente> call, Response<Cliente> response) {
                if (response.isSuccessful() && response.code() == 200) {
                    Cliente clientes = response.body();
                    cliente = clientes;
                    Log.d("Cliente DataService", String.valueOf(cliente));
                }
            }

            @Override
            public void onFailure(Call<Cliente> call, Throwable t) {

            }

        });

        return cliente;
    }

    public List<Servicio> getServicios(int cochera_id) {
        ParkingApi parkingApi = RetrofitInstance.createService(ParkingApi.class);
        //        retrofitInstance = new RetrofitInstance();

        Call<List<Servicio>> callServicio = parkingApi.getServicios(cochera_id);
        callServicio.enqueue(new Callback<List<Servicio>>() {
            @Override
            public void onResponse(Call<List<Servicio>> call, Response<List<Servicio>> response) {
                if (response.isSuccessful()) {
                    List<Servicio> servicios = response.body();
                    servicioList.addAll(servicios);
                }
            }

            @Override
            public void onFailure(Call<List<Servicio>> call, Throwable t) {
                Log.e("Wiwi", t.getLocalizedMessage());
            }
        });

        return servicioList;
    }

    public void setReserva(Reserva reserva) {
        //        retrofitInstance = new RetrofitInstance();
        ParkingApi parkingApi = RetrofitInstance.createService(ParkingApi.class);
        Call<Reserva> callReserva = parkingApi.setReserva(reserva);
        callReserva.enqueue(new Callback<Reserva>() {
            @Override
            public void onResponse(Call<Reserva> call, Response<Reserva> response) {
            }

            @Override
            public void onFailure(Call<Reserva> call, Throwable t) {

            }
        });
    }

    public void setCliente(Cliente cliente) {
        //        retrofitInstance = new RetrofitInstance();
        ParkingApi parkingApi = RetrofitInstance.createService(ParkingApi.class);
        Call<Cliente> callCliente = parkingApi.setCliente(cliente);

        callCliente.enqueue(new Callback<Cliente>() {
            @Override
            public void onResponse(Call<Cliente> call, Response<Cliente> response) {

            }

            @Override
            public void onFailure(Call<Cliente> call, Throwable t) {

            }
        });
    }

    public void updateCliente(int id, Cliente cliente) {
        //        retrofitInstance = new RetrofitInstance();
        ParkingApi parkingApi = RetrofitInstance.createService(ParkingApi.class);
        Call<Cliente> callCliente = parkingApi.updateCliente(id, cliente);

        callCliente.enqueue(new Callback<Cliente>() {
            @Override
            public void onResponse(Call<Cliente> call, Response<Cliente> response) {

            }

            @Override
            public void onFailure(Call<Cliente> call, Throwable t) {

            }
        });
    }
}
