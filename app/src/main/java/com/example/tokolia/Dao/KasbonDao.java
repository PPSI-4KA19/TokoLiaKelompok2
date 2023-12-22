package com.example.tokolia.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.tokolia.Entites.Kasbon;

import java.util.List;

@Dao
public interface KasbonDao {

    @Insert
    void insert(Kasbon kasbon);

    @Update
    void update(Kasbon kasbon);

    @Delete
    void delete(Kasbon kasbon);

    @Query("SELECT * FROM kasbon ORDER BY pemilik_kasbon ASC")
    LiveData<List<Kasbon>> getAllKasbon();

}
