package com.example.thirdeye;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;

@Database(entities = {Settings.class},version = 3)
public abstract class MyRoomDatabase extends RoomDatabase {
    public abstract UserDao userDao();
    private static volatile MyRoomDatabase INSTANCE;
    static MyRoomDatabase getInstance(Context context){
        if (INSTANCE == null) {
            synchronized (MyRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    MyRoomDatabase.class, "My_Database")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
