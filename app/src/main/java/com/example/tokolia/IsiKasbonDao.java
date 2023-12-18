package com.example.tokolia;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface IsiKasbonDao {

    @Insert
    void insert(IsiKasbon isiKasbon);

    @Update
    void update(IsiKasbon isiKasbon);

    @Delete
    void delete(IsiKasbon isiKasbon);

    @Query("SELECT * FROM isi_kasbon ORDER BY pemilik_kasbon ASC")
    LiveData<List<IsiKasbon>> getAllIsiKasbon();

}
