package com.example.tokolia.Entites;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "transaksi")
public class Transaksi {

    @NonNull
    @PrimaryKey(autoGenerate = false)
    String id_transaksi;
    String tanggal;

    String jenis_transaksi;
    @Nullable
    String kasbon;

    public Transaksi(String id_transaksi, String tanggal, String jenis_transaksi, @Nullable String kasbon) {
        this.id_transaksi = id_transaksi;
        this.tanggal = tanggal;
        this.jenis_transaksi = jenis_transaksi;
        this.kasbon = kasbon;
    }

    public void setId_transaksi(String id_transaksi) {
        this.id_transaksi = id_transaksi;
    }

    public String getId_transaksi() {
        return id_transaksi;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getJenis_transaksi() {
        return jenis_transaksi;
    }

    public String getKasbon() {
        return kasbon;
    }

    public void setKasbon(String kasbon) {
        this.kasbon = kasbon;
    }

    public void setJenis_transaksi(String jenis_transaksi) {
        this.jenis_transaksi = jenis_transaksi;
    }
}
