package com.example.parqueaya;

import android.app.Application;

public class MyApplication extends Application {
    private int reservaId;
    private int vehiculoId;
    private double precio_aproximado;
    private int cochera_id;
    private int cliente_id;
    private String placa;
    private String nombre;
    private String fecha;

    public int getCochera_id() {
        return cochera_id;
    }

    public void setCochera_id(int cochera_id) {
        this.cochera_id = cochera_id;
    }

    public int getCliente_id() {
        return cliente_id;
    }

    public void setCliente_id(int cliente_id) {
        this.cliente_id = cliente_id;
    }

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
