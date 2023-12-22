package com.example.tokolia.Entites;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.example.tokolia.Entites.Kasbon;
import com.example.tokolia.Entites.Transaksi;

import java.util.List;

public class KasbonTransaksi {
    @Embedded
    public Kasbon kasbon;
    @Relation(
            parentColumn = "pemilik_kasbon",
            entityColumn = "kasbon"
    )
    public List<Transaksi> transaksis;
}
