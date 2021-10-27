package com.example.androidexmaple2.data.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.androidexmaple2.data.model.CountryModel;

@androidx.room.Database(entities = CountryModel.class,version = 1,exportSchema = false)
public abstract class Database extends RoomDatabase {
    private static Database instance;

    public abstract CountryDao countryDao();

    public static synchronized Database getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(), Database.class , "Database_name")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallBack)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallBack = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            // new PopulateDb(instance).execute();
        }
    };
}
