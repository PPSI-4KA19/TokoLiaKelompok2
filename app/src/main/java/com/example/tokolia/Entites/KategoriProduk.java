package com.example.tokolia.Entites;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.example.tokolia.Entites.Kategori;
import com.example.tokolia.Entites.Produk;

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
