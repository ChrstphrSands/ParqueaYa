package com.example.parqueaya.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Servicio implements Serializable {
    public Servicio() {
    }

    @SerializedName("Descripcion")
    private String descripcion;

    @SerializedName("Costo")
    private double costo;

    @SerializedName("EsPorHora")
    private boolean esPorHora;

    @SerializedName("ServicioId")
    private int id;

    @SerializedName("CocheraId")
    private String cocheraId;


    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setCosto(double costo) {
        this.costo = costo;
    }

    public double getCosto() {
        return costo;
    }

    public void setEsPorHora(boolean esPorHora) {
        this.esPorHora = esPorHora;
    }

    public boolean getEsPorHora() {
        return esPorHora;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setCocheraId(String cocheraId) {
        this.cocheraId = cocheraId;
    }

    public String getCocheraId() {
        return cocheraId;
    }

    @Override
    public String toString() {
        return
            "Servicio{" +
                "descripcion = '" + descripcion + '\'' +
                ",costo = '" + costo + '\'' +
                ",esPorHora = '" + esPorHora + '\'' +
                ",id = '" + id + '\'' +
                ",cochera_id = '" + cocheraId + '\'' +
                "}";
    }
}
