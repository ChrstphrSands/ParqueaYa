package com.example.parqueaya.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "historial")
public class Historial {

    public Historial() {
    }

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "cliente_id")
    private int cliente_id;

    @ColumnInfo(name = "nombre")
    private String nombre;

    @ColumnInfo(name = "nombre_cochera")
    private String nombre_cochera;

    @ColumnInfo(name = "fecha_reserva")
    private String fecha_reserva;

    @ColumnInfo(name = "total")
    private String total;

    @ColumnInfo(name = "tiempo_reserva")
    private String tiempo_reserva;

    @ColumnInfo(name = "placa")
    private String placa;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCliente_id() {
        return cliente_id;
    }

    public void setCliente_id(int cliente_id) {
        this.cliente_id = cliente_id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre_cochera() {
        return nombre_cochera;
    }

    public void setNombre_cochera(String nombre_cochera) {
        this.nombre_cochera = nombre_cochera;
    }

    public String getFecha_reserva() {
        return fecha_reserva;
    }

    public void setFecha_reserva(String fecha_reserva) {
        this.fecha_reserva = fecha_reserva;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getTiempo_reserva() {
        return tiempo_reserva;
    }

    public void setTiempo_reserva(String tiempo_reserva) {
        this.tiempo_reserva = tiempo_reserva;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    @Override
    public String toString() {
        return "Historial{" +
            "id=" + id +
            ", cliente_id=" + cliente_id +
            ", nombre='" + nombre + '\'' +
            ", nombre_cochera='" + nombre_cochera + '\'' +
            ", fecha_reserva='" + fecha_reserva + '\'' +
            ", total='" + total + '\'' +
            ", tiempo_reserva='" + tiempo_reserva + '\'' +
            ", placa='" + placa + '\'' +
            '}';
    }
}
