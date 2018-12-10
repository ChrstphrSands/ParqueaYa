package com.example.parqueaya.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "calificacion")
public class Calificacion {


    public Calificacion() {
    }

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "cliente_id")
    private int cliente_id;

    @ColumnInfo(name = "cochera_id")
    private int cochera_id;

    @ColumnInfo(name = "puntaje")
    private float puntaje;

    @ColumnInfo(name = "comentario")
    private String comentario;

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

    public int getCochera_id() {
        return cochera_id;
    }

    public void setCochera_id(int cochera_id) {
        this.cochera_id = cochera_id;
    }

    public float getPuntaje() {
        return puntaje;
    }

    public void setPuntaje(float puntaje) {
        this.puntaje = puntaje;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    @Override
    public String toString() {
        return "Calificacion{" +
            "id=" + id +
            ", cliente_id=" + cliente_id +
            ", cochera_id=" + cochera_id +
            ", puntaje=" + puntaje +
            ", comentario='" + comentario + '\'' +
            '}';
    }
}
