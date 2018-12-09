package com.example.parqueaya.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "favorito")
public class Favorito {

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "id")
    public String id;

    @ColumnInfo(name = "nombre")
    public String nombre;

    @ColumnInfo(name = "precio")
    public String precio;

    @ColumnInfo(name = "foto")
    public String foto;

    @ColumnInfo(name = "cocheraId")
    public String cocheraId;

}
