package com.example.tokolia.Entites;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "transaksi")
public class Transaksi {

    @PrimaryKey(autoGenerate = true)
    int id_transaksi;
    Date tanggal;
    String jenis_transaksi;
    String kasbon;

    public Transaksi(Date tanggal, String jenis_transaksi, String kasbon) {
        this.tanggal = tanggal;
        this.jenis_transaksi = jenis_transaksi;
        this.kasbon = kasbon;
    }

    public void setId_transaksi(int id_transaksi) {
        this.id_transaksi = id_transaksi;
    }

    public int getId_transaksi() {
        return id_transaksi;
    }

    public Date getTanggal() {
        return tanggal;
    }

    public void setTanggal(Date tanggal) {
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
