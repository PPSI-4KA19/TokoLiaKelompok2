package com.example.tokolia;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.List;

@Dao
public interface KategoriDao {

    @Insert
    void insert(Kategori kategori);
    @Update
    void update(Kategori kategori);
    @Delete
    void delete(Kategori kategori);

    @Query("SELECT * FROM kategori ORDER BY nama_kategori ASC")
    LiveData<List<Kategori>> getAllKategori();

    @Transaction
    @Query("DELETE FROM kategori WHERE nama_kategori = :namaKategori")
    void deleteKategoriSpesifik(String namaKategori);
}
