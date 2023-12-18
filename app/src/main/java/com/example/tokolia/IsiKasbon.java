package com.example.tokolia;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "isi_kasbon", primaryKeys = {"pemilik_kasbon","id_transaksi"})
public class IsiKasbon {
    @NonNull
    String pemilik_kasbon;

    int id_transaksi;

    public IsiKasbon(String pemilik_kasbon, int id_transaksi) {
        this.pemilik_kasbon = pemilik_kasbon;
        this.id_transaksi = id_transaksi;
    }

    public String getPemilik_kasbon() {
        return pemilik_kasbon;
    }

    public void setPemilik_kasbon(String pemilik_kasbon) {
        this.pemilik_kasbon = pemilik_kasbon;
    }

    public int getId_transaksi() {
        return id_transaksi;
    }

    public void setId_transaksi(int id_transaksi) {
        this.id_transaksi = id_transaksi;
    }
}
