package com.example.parqueaya;

import android.app.Application;

public class MyApplication extends Application {
    private int reservaId;
    private int vehiculoId;
    private double precio_aproximado;
    private String placa;
    private String nombre;
    private String fecha;

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getReservaId() {
        return reservaId;
    }

    public void setReservaId(int reservaId) {
        this.reservaId = reservaId;
    }

    public int getVehiculoId() {
        return vehiculoId;
    }

    public void setVehiculoId(int vehiculoId) {
        this.vehiculoId = vehiculoId;
    }

    public double getPrecio_aproximado() {
        return precio_aproximado;
    }

    public void setPrecio_aproximado(double precio_aproximado) {
        this.precio_aproximado = precio_aproximado;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
