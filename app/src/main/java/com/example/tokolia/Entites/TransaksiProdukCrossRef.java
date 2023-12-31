package com.example.tokolia.Entites;

import androidx.annotation.NonNull;
import androidx.room.Entity;

@Entity(tableName = "crossref",primaryKeys = {"id_transaksi","id_produk"} )
public class TransaksiProdukCrossRef {
    @NonNull
    String id_transaksi;
    int id_produk;
    int quantity;
    int total;



    public TransaksiProdukCrossRef(String id_transaksi, int id_produk, int quantity, int total) {
        this.id_transaksi = id_transaksi;
        this.id_produk = id_produk;
        this.quantity = quantity;
        this.total = total;
    }

    public String getId_transaksi() {
        return id_transaksi;
    }

    public void setId_transaksi(String id_transaksi) {
        this.id_transaksi = id_transaksi;
    }

    public int getId_produk() {
        return id_produk;
    }

    public void setId_produk(int id_produk) {
        this.id_produk = id_produk;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

}
