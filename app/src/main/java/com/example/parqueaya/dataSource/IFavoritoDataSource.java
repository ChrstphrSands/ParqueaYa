package com.example.parqueaya.dataSource;

import com.example.parqueaya.models.Favorito;
import io.reactivex.Flowable;

import java.util.List;

public interface IFavoritoDataSource {

    Flowable<List<Favorito>> getFavoritos();

    int isFavorito(int itemId);

    void insertFavorito(Favorito... favoritos);

    void delete(Favorito favorito);

}
