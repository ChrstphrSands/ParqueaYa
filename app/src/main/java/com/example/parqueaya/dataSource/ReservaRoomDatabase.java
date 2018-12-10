package com.example.parqueaya.dataSource;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import com.example.parqueaya.models.Favorito;

@Database(entities = {Favorito.class}, version = 4)
public abstract class ReservaRoomDatabase extends RoomDatabase {

    public abstract FavoritoDao favoritoDao();

//    private static ReservaRoomDatabase instance;

//    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
//        @Override
//        public void migrate(SupportSQLiteDatabase database) {
//            // Since we didn't alter the table, there's nothing else to do here.
//        }
//    };
//
//    public static ReservaRoomDatabase getInstance(Context context) {
//        if (instance == null) {
//            instance = Room.databaseBuilder(context.getApplicationContext(), ReservaRoomDatabase.class, "ParqueoDB")
//                .allowMainThreadQueries()
//                .build();
//        }
//        return instance;
//    }
}