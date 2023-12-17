package com.example.tokolia;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "kategoriproduk")
public class KategoriProduk {

    @PrimaryKey(autoGenerate = false)
    String nama_kategori;
    @PrimaryKey(autoGenerate = false)
    int id_produk;

    public String getNama_kategori() {
        return nama_kategori;
    }

    public void setNama_kategori(String nama_kategori) {
        this.nama_kategori = nama_kategori;
    }

    public int getId_produk() {
        return id_produk;
    }

    public void setId_produk(int id_produk) {
        this.id_produk = id_produk;
    }
}
