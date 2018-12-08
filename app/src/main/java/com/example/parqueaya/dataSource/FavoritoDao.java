package com.example.parqueaya.dataSource;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import com.example.parqueaya.models.Favorito;
import io.reactivex.Flowable;
import android.arch.persistence.room.Delete;

import java.util.List;

@Dao
public interface FavoritoDao {

    @Query("SELECT * FROM favorito")
    Flowable<List<Favorito>> getFavoritos();

    @Query("SELECT EXISTS (SELECT 1 FROM favorito WHERE id=:itemId)")
    int isFavorito(int itemId);

    @Insert
    void insertFavorito(Favorito... favoritos);

    @Delete
    void delete(Favorito favorito);
}
