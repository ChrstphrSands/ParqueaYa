package com.example.parqueaya.dataSource;

import com.example.parqueaya.models.Favorito;
import io.reactivex.Flowable;

import java.util.List;

public class FavoritoDataSource implements IFavoritoDataSource {

    private FavoritoDao favoritoDao;
    private static FavoritoDataSource instance;

    public FavoritoDataSource(FavoritoDao favoritoDao) {
        this.favoritoDao = favoritoDao;
    }

    public static FavoritoDataSource getInstance(FavoritoDao favoritoDao) {
        if (instance == null) {
            instance = new FavoritoDataSource(favoritoDao);
        }
        return instance;
    }

    @Override
    public Flowable<List<Favorito>> getFavoritos() {
        return favoritoDao.getFavoritos();
    }

    @Override
    public int isFavorito(int itemId) {
        return favoritoDao.isFavorito(itemId);
    }

    @Override
    public void insertFavorito(Favorito... favoritos) {
        favoritoDao.insertFavorito(favoritos);
    }

    @Override
    public void delete(Favorito favorito) {
        favoritoDao.delete(favorito);
    }
}