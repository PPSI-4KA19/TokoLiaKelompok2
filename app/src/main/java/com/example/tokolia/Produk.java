package com.example.tokolia;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "produk")
public class Produk {

    @PrimaryKey(autoGenerate = true)
    int id_produk;

    String nama_produk;
    int harga_jual;
    int harga_modal;
    int stok;
    String kategori;

    public Produk(String nama_produk,int harga_jual,int harga_modal, int stok, String kategori){
        this.nama_produk = nama_produk;
        this.harga_jual = harga_jual;
        this.harga_modal = harga_modal;
        this.stok = stok;
        this.kategori = kategori;
    }

    public int getId_produk() {
        return id_produk;
    }

    public String getNama_produk() {
        return nama_produk;
    }

    public int getHarga_jual() {
        return harga_jual;
    }

    public int getHarga_modal() {
        return harga_modal;
    }

    public int getStok() {
        return stok;
    }

    public String getKategori() {
        return kategori;
    }

    public void setId_produk(int id_produk) {
        this.id_produk = id_produk;
    }

    public void setNama_produk(String nama_produk) {
        this.nama_produk = nama_produk;
    }

    public void setHarga_jual(int harga_jual) {
        this.harga_jual = harga_jual;
    }

    public void setHarga_modal(int harga_modal) {
        this.harga_modal = harga_modal;
    }

    public void setStok(int stok) {
        this.stok = stok;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }
}
