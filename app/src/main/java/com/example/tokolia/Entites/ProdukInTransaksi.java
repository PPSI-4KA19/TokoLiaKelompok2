package com.example.tokolia.Entites;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import com.example.tokolia.Produk;

import java.util.List;

public class ProdukInTransaksi {
    @Embedded
    public Transaksi transaksi;
    @Relation(
            parentColumn = "id_transaksi",
            entityColumn = "id_produk",
            associateBy = @Junction(TransaksiProdukCrossRef.class)
    )
    public List<Produk> produks;
}
