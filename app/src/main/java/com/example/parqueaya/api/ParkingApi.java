package com.example.parqueaya.api;

import com.example.parqueaya.models.*;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface ParkingApi {
    @GET("cocheras")
    Call<List<Cochera>> getCocheras();

    @GET("cocheras/{id}")
    Call<Cochera> getCochera(@Path("id") int id);

    @GET("clientes")
    Call<List<Cliente>> getClientes();

    @GET("clientes/{id}")
    Call<Cliente> getCliente(@Path("id") int id);

    @GET("clientes")
    Call<Cliente> getClienteDetail(@Query("UID") String UID);

    @POST("clientes")
    Call<Cliente> setCliente(@Body Cliente cliente);

    @PUT("clientes/{id}")
    Call<Cliente> updateCliente(@Path("id") int id, @Body Cliente cliente);

    @GET("servicios")
    Call<List<Servicio>> getServicios(@Query("CocheraId") int CocheraId);

    @POST("reservas")
    Call<Reserva> setReserva(@Body Reserva reserva);

    @POST("vehiculos")
    Call<Vehiculo> setVehiculo(@Body Vehiculo vehiculo);
}
