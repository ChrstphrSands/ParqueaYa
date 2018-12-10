package com.example.parqueaya.models;

import com.google.gson.annotations.SerializedName;

public class Vehiculo {
    public Vehiculo() {
    }

    public Vehiculo(String placa, int tipoVehiculoId, int clienteId) {
        this.placa = placa;
        this.tipoVehiculoId = tipoVehiculoId;
        this.clienteId = clienteId;
    }

    public Vehiculo(int id, String placa, int tipoVehiculoId) {
        this.id = id;
        this.placa = placa;
        this.tipoVehiculoId = tipoVehiculoId;
    }

    @SerializedName("VehiculoId")
    private int id;

    @SerializedName("Placa")
    private String placa;

    @SerializedName("TipoVehiculoId")
    private int tipoVehiculoId;

    @SerializedName("ClienteId")
    private int clienteId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getPlaca() {
        return placa;
    }

    public int getTipoVehiculoId() {
        return tipoVehiculoId;
    }

    public void setTipoVehiculoId(int tipoVehiculoId) {
        this.tipoVehiculoId = tipoVehiculoId;
    }

    public int getClienteId() {
        return clienteId;
    }

    public void setClienteId(int clienteId) {
        this.clienteId = clienteId;
    }

    @Override
    public String toString() {
        return "Vehiculo{" +
            "id=" + id +
            ", placa='" + placa + '\'' +
            ", tipoVehiculoId=" + tipoVehiculoId +
            ", clienteId=" + clienteId +
            '}';
    }
}
