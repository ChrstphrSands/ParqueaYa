package com.example.parqueaya.dataSource;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import com.example.parqueaya.models.Favorito;
import android.arch.persistence.room.Delete;

import java.util.List;

@Dao
public interface FavoritoDao {

    @Query("SELECT * FROM favorito")
    List<Favorito> getFavoritos();

    @Query("SELECT 1 FROM favorito WHERE id=:id")
    int isFavorito(int id);

    @Insert
    void insertFavorito(Favorito favoritos);

    @Delete
    void delete(Favorito favorito);
}
