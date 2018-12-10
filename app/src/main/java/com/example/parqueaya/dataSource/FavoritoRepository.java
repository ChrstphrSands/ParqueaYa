package com.example.parqueaya.dataSource;

import com.example.parqueaya.models.Favorito;
import io.reactivex.Flowable;

import java.util.List;

public class FavoritoRepository implements IFavoritoDataSource {

    private IFavoritoDataSource favoritoDataSource;

    public FavoritoRepository(IFavoritoDataSource favoritoDataSource) {
        this.favoritoDataSource = favoritoDataSource;
    }

    private static FavoritoRepository instance;

    public static FavoritoRepository getInstance(IFavoritoDataSource favoritoDataSource) {
        if (instance == null) {
            instance = new FavoritoRepository(favoritoDataSource);
        }

        return instance;
    }

    @Override
    public List<Favorito> getFavoritos() {
        return favoritoDataSource.getFavoritos();
    }

    @Override
    public int isFavorito(int itemId) {
        return favoritoDataSource.isFavorito(itemId);
    }

    @Override
    public void insertFavorito(Favorito favoritos) {
        favoritoDataSource.insertFavorito(favoritos);
    }

    @Override
    public void delete(Favorito favorito) {
        favoritoDataSource.delete(favorito);
    }
}
