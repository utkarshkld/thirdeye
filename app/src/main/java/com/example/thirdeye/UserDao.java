package com.example.thirdeye;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;
@Dao
public interface UserDao {
    @Insert
    void insert(Settings t);
    @Delete
    void delete(Settings t);
    @Query("SELECT * FROM settings")
    List<Settings> getAll();
    // we will use getAll() method to check wether there is any entry in the database
    // if there is so we will use that for setting the app
    // insert and delete will be used in case of changing the settings
    // when first time the app will open it shld not contain any entry in database

}
