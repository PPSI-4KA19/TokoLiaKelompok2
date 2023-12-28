package com.example.tokolia.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.tokolia.Entites.Pencatatan;

import java.util.List;

@Dao
public interface PencatatanDao {

    @Insert
    void insertPencatatan(Pencatatan pencatatan);

    @Update
    void updatePencatatan(Pencatatan pencatatan);

    @Query("SELECT * FROM pencatatan")
    LiveData<List<Pencatatan>> getAllPencatatan();
}
