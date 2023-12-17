package com.example.tokolia;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "kategori")
public class Kategori {

    @PrimaryKey(autoGenerate = false)
    public String nama_kategori;
    public String deskripsi;

    public Kategori(String nama,String deskripsi){
        this.nama_kategori = nama;
        this.deskripsi = deskripsi;
    }

    public String getNama_kategori() {
        return nama_kategori;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setNama_kategori(String nama_kategori) {
        this.nama_kategori = nama_kategori;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }
}
