package com.example.tokolia;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "kategori")
public class Kategori {

    @NonNull
    @PrimaryKey(autoGenerate = false)
    public String nama_kategori;
    public String deskripsi;

    public Kategori(String nama_kategori,String deskripsi){
        this.nama_kategori = nama_kategori;
        this.deskripsi = deskripsi;
    }

    public String getNamakategori() {
        return nama_kategori;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setNamaKategori(String nama_kategori) {
        this.nama_kategori = nama_kategori;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }
}
