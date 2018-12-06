package com.example.parqueaya.models;

import com.google.gson.annotations.SerializedName;

public class Vehiculo {
    public Vehiculo() {
    }

    @SerializedName("VehiculoId")
    private int id;

    @SerializedName("Placa")
    private String placa;

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

    @Override
    public String toString() {
        return
            "VehiculosItem{" +
                "id = '" + id + '\'' +
                ",placa = '" + placa + '\'' +
                "}";
    }
}
