package com.example.parqueaya.dataSource;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import com.example.parqueaya.models.Favorito;

@Database(entities = {Favorito.class}, version = 1)
public abstract class ReservaRoomDatabase extends RoomDatabase {

    public abstract FavoritoDao favoritoDao();

    private static ReservaRoomDatabase instance;

    public static ReservaRoomDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context, ReservaRoomDatabase.class, "ParqueoDB")
                .allowMainThreadQueries()
                .build();
        }
        return instance;
    }

}
