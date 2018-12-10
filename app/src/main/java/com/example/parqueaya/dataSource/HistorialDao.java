package com.example.parqueaya.dataSource;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import com.example.parqueaya.models.Historial;

import java.util.List;

@Dao
public interface HistorialDao {
    @Query("SELECT * FROM historial")
    List<Historial> getHistorial();

//    @Query("SELECT 1 FROM favorito WHERE id=:id")
//    int isFavorito(int id);

    @Insert
    void insertHistorial(Historial historial);

//    @Query("DELETE FROM favorito WHERE id=:id")
//    void deleteById(int id);
//
//    @Delete
//    void deletes(Favorito favorito);
//
//    @Delete
//    void delete(Favorito favorito);
}
