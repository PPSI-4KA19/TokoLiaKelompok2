package com.example.tokolia.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.example.tokolia.Entites.Produk;

import java.util.List;

@Dao
public interface ProdukDao {
    @Insert
    void insert(Produk produk);

    @Update
    void update(Produk produk);

    @Delete
    void delete(Produk produk);

    @Transaction
    @Query("SELECT * from produk ORDER BY id_produk ASC")
    LiveData<List<Produk>> getAllProduk();

    @Transaction
    @Query("SELECT * from produk WHERE kategori = :kategoriSearch")
    LiveData<List<Produk>> getSelectedProdukOnKategori(String kategoriSearch);

    @Transaction
    @Query("UPDATE produk SET kategori = :kategoriBaru WHERE kategori = :kategoriLama")
    void updateKategoriOnProduk(String kategoriLama, String kategoriBaru);

    @Transaction
    @Query("DELETE from produk WHERE kategori = :namaKategori")
    void deleteAllProdukOnKategori(String namaKategori);
}
