package com.example.tokolia;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Kategori.class, KategoriProduk.class, Produk.class
                    , Transaksi.class, TransaksiProduk.class
                    , Kasbon.class, IsiKasbon.class},version = 1)
public abstract class TokoDatabase extends RoomDatabase {

    private static TokoDatabase instance;


    //Dao untuk proses produk + kategori
    public abstract ProdukDao produkDao();
    public abstract KategoriDao kategoriDao();
    public abstract KategoriProdukDao kategoriProdukDao();

    //Dao untuk proses transaksi + produk

    //Dao untuk proses kasbon + transaksi

    public static synchronized TokoDatabase getInstance(Context context){

        if(instance == null){

            instance = Room.databaseBuilder(context.getApplicationContext(),
                    TokoDatabase.class, "toko_database")
                    .fallbackToDestructiveMigration().build();

        }

        return instance;

    }

}
