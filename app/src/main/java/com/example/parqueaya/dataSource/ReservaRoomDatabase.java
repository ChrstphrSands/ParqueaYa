package com.example.parqueaya.dataSource;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import com.example.parqueaya.models.Calificacion;
import com.example.parqueaya.models.Favorito;
import com.example.parqueaya.models.Historial;

@Database(entities = {Favorito.class, Calificacion.class, Historial.class}, version = 7)
public abstract class ReservaRoomDatabase extends RoomDatabase {

    public abstract FavoritoDao favoritoDao();

    public abstract CalificacionDao calificacionDao();

    public abstract HistorialDao historialDao();

}