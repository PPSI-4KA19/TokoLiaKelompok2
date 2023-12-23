package com.example.tokolia.Entites;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import java.util.List;

public class TransaksiInProduk {
    @Embedded
    public Produk produk;
    @Relation(
            parentColumn = "id_produk",
            entityColumn = "id_transaksi",
            associateBy = @Junction(TransaksiProdukCrossRef.class)
    )
    public List<Transaksi> transaksis;
}
