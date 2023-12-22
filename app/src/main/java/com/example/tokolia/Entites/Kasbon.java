package com.example.tokolia.Entites;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "kasbon")
public class Kasbon {
    @NonNull
    @PrimaryKey(autoGenerate = false)
    String pemilik_kasbon;

    int total_hutang;
    int sisa_hutang;

    public Kasbon(String pemilik_kasbon, int total_hutang, int sisa_hutang) {
        this.pemilik_kasbon = pemilik_kasbon;
        this.total_hutang = total_hutang;
        this.sisa_hutang = sisa_hutang;
    }

    public String getPemilik_kasbon() {
        return pemilik_kasbon;
    }

    public void setPemilik_kasbon(String pemilik_kasbon) {
        this.pemilik_kasbon = pemilik_kasbon;
    }

    public int getTotal_hutang() {
        return total_hutang;
    }

    public void setTotal_hutang(int total_hutang) {
        this.total_hutang = total_hutang;
    }

    public int getSisa_hutang() {
        return sisa_hutang;
    }

    public void setSisa_hutang(int sisa_hutang) {
        this.sisa_hutang = sisa_hutang;
    }
}
