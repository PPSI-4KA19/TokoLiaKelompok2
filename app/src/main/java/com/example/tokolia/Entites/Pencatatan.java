package com.example.tokolia.Entites;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity (tableName = "pencatatan")
public class Pencatatan {
    @PrimaryKey(autoGenerate = true)
    int id;
    String jenis;
    int nominal;

    public Pencatatan(String jenis, int nominal) {
        this.jenis = jenis;
        this.nominal = nominal;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getJenis() {
        return jenis;
    }

    public void setJenis(String jenis) {
        this.jenis = jenis;
    }

    public int getNominal() {
        return nominal;
    }

    public void setNominal(int nominal) {
        this.nominal = nominal;
    }
}
