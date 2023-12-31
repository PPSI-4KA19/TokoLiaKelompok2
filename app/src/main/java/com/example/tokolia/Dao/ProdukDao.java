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

    //mungkin dibutuhkan kalo ada fitur edit nama kategori
    @Transaction
    @Query("UPDATE produk SET kategori = :kategoriBaru WHERE kategori = :kategoriLama")
    void updateKategoriOnProduk(String kategoriLama, String kategoriBaru);

    @Transaction
    @Query("DELETE from produk WHERE kategori = :namaKategori")
    void deleteAllProdukOnKategori(String namaKategori);

    @Transaction
    @Query("SELECT * FROM produk WHERE id_produk = :id_produk")
    LiveData<List<Produk>> getListProdukById(int id_produk);

    @Transaction
    @Query("UPDATE produk SET stok = stok - :jumlah WHERE id_produk = :id_produk")
    void decreaseProdukStok(int id_produk, int jumlah);

    @Transaction
    @Query("UPDATE produk SET stok = stok + :jumlah WHERE id_produk = :id_produk")
    void increaseProdukStok(int id_produk, int jumlah);

    @Transaction
    @Query("SELECT * FROM produk WHERE id_produk = :id")
    List<Produk> getProduksById(int id);

    @Transaction
    @Query("SELECT * FROM produk ORDER BY id_produk ASC")
    List<Produk> getProduks();
}
