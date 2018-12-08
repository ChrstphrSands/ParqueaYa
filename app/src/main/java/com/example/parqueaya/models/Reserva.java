package com.example.parqueaya.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Reserva implements Serializable {
    public Reserva() {
    }

    public Reserva(String horaReservaLlegada, Double costo, int reservaEstadoId, int vehiculoId, int servicioId, String
        horaSistemaLlegada, String horaReservaSalida, String horaSistemaSalida) {
        this.horaReservaLlegada = horaReservaLlegada;
        this.costo = costo;
        this.reservaEstadoId = reservaEstadoId;
        this.vehiculoId = vehiculoId;
        this.servicioId = servicioId;
        this.horaSistemaLlegada = horaSistemaLlegada;
        this.horaReservaSalida = horaReservaSalida;
        this.horaSistemaSalida = horaSistemaSalida;
    }

    @SerializedName("hora_reserva_llegada")
    private String horaReservaLlegada;

    @SerializedName("costo")
    private Double costo;

    @SerializedName("reserva_estado_id")
    private int reservaEstadoId;

    @SerializedName("vehiculo_id")
    private int vehiculoId;

    @SerializedName("servicio_id")
    private int servicioId;

    @SerializedName("hora_sistema_llegada")
    private String horaSistemaLlegada;

    @SerializedName("id")
    private int id;

    @SerializedName("hora_reserva_salida")
    private String horaReservaSalida;

    @SerializedName("hora_sistema_salida")
    private String horaSistemaSalida;

    public void setHoraReservaLlegada(String horaReservaLlegada) {
        this.horaReservaLlegada = horaReservaLlegada;
    }

    public String getHoraReservaLlegada() {
        return horaReservaLlegada;
    }

    public void setCosto(Double costo) {
        this.costo = costo;
    }

    public Double getCosto() {
        return costo;
    }

    public void setReservaEstadoId(int reservaEstadoId) {
        this.reservaEstadoId = reservaEstadoId;
    }

    public int getReservaEstadoId() {
        return reservaEstadoId;
    }

    public void setVehiculoId(int vehiculoId) {
        this.vehiculoId = vehiculoId;
    }

    public int getVehiculoId() {
        return vehiculoId;
    }

    public void setServicioId(int servicioId) {
        this.servicioId = servicioId;
    }

    public int getServicioId() {
        return servicioId;
    }

    public void setHoraSistemaLlegada(String horaSistemaLlegada) {
        this.horaSistemaLlegada = horaSistemaLlegada;
    }

    public String getHoraSistemaLlegada() {
        return horaSistemaLlegada;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setHoraReservaSalida(String horaReservaSalida) {
        this.horaReservaSalida = horaReservaSalida;
    }

    public String getHoraReservaSalida() {
        return horaReservaSalida;
    }

    public void setHoraSistemaSalida(String horaSistemaSalida) {
        this.horaSistemaSalida = horaSistemaSalida;
    }

    public String getHoraSistemaSalida() {
        return horaSistemaSalida;
    }

    @Override
    public String toString() {
        return
            "Reserva{" +
                "hora_reserva_llegada = '" + horaReservaLlegada + '\'' +
                ",costo = '" + costo + '\'' +
                ",reserva_estado_id = '" + reservaEstadoId + '\'' +
                ",vehiculo_id = '" + vehiculoId + '\'' +
                ",servicio_id = '" + servicioId + '\'' +
                ",hora_sistema_llegada = '" + horaSistemaLlegada + '\'' +
                ",id = '" + id + '\'' +
                ",hora_reserva_salida = '" + horaReservaSalida + '\'' +
                ",hora_sistema_salida = '" + horaSistemaSalida + '\'' +
                "}";
    }
}
