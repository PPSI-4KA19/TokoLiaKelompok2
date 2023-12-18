package com.example.tokolia;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "kategoriproduk",primaryKeys = {"nama_kategori","id_produk"})
public class KategoriProduk {

    @NonNull
    String nama_kategori;
    int id_produk;

    public KategoriProduk(String nama_kategori, int id_produk) {
        this.nama_kategori = nama_kategori;
        this.id_produk = id_produk;
    }

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
