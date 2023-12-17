package com.example.tokolia;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ProdukDao {
    @Insert
    void insert(Produk produk);

    @Update
    void update(Produk produk);

    @Delete
    void delete(Produk produk);

    @Query("SELECT * from produk ORDER BY id_produk ASC")
    LiveData<List<Produk>> getAllProduk();
}
