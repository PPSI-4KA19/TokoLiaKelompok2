package com.example.tokolia;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface KategoriProdukDao {

    @Insert
    void insert(KategoriProduk kp);

    @Update
    void update(KategoriProduk kp);

    @Delete
    void delete(KategoriProduk kp);

    @Query("SELECT * FROM kategoriproduk ORDER BY nama_kategori ASC")
    LiveData<List<KategoriProduk>> getAllKatProduk();

}
