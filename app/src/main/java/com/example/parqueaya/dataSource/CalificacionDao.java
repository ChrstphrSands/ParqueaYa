package com.example.parqueaya.dataSource;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import com.example.parqueaya.models.Calificacion;

import java.util.List;

@Dao
public interface CalificacionDao {

    @Insert
    void insertCalificacion(Calificacion calificacion);

    @Query("Select * from calificacion where cochera_id=:cochera_id")
    List<Calificacion> getCalificaciones(int cochera_id);


}
