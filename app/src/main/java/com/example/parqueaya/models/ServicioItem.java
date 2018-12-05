package com.example.parqueaya.models;

import com.google.gson.annotations.SerializedName;

class ServicioItem {
    @SerializedName("EsPorHora")
    private boolean esPorHora;

    @SerializedName("Costo")
    private double costo;

    @SerializedName("Descripcion")
    private String descripcion;

    @SerializedName("id")
    private int id;

    @SerializedName("CocheraId")
    private int cocheraId;

    public void setEsPorHora(boolean esPorHora){
        this.esPorHora = esPorHora;
    }

    public boolean isEsPorHora(){
        return esPorHora;
    }

    public void setCosto(double costo){
        this.costo = costo;
    }

    public double getCosto(){
        return costo;
    }

    public void setDescripcion(String descripcion){
        this.descripcion = descripcion;
    }

    public String getDescripcion(){
        return descripcion;
    }

    public void setId(int id){
        this.id = id;
    }

    public int getId(){
        return id;
    }

    public void setCocheraId(int cocheraId){
        this.cocheraId = cocheraId;
    }

    public int getCocheraId(){
        return cocheraId;
    }

    @Override
    public String toString(){
        return
            "ServicioItem{" +
                "esPorHora = '" + esPorHora + '\'' +
                ",costo = '" + costo + '\'' +
                ",descripcion = '" + descripcion + '\'' +
                ",id = '" + id + '\'' +
                ",cocheraId = '" + cocheraId + '\'' +
                "}";
    }
}
