package com.example.tokolia;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class KategoriProduk {
    @Embedded
    public Kategori kategori;
    @Relation(
            parentColumn = "nama_kategori",
            entityColumn = "kategori"
    )
    public List<Produk> produks;
}
