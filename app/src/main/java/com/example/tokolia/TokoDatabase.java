package com.example.tokolia;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Kategori.class, KategoriProduk.class, Produk.class
                    , Transaksi.class, TransaksiProduk.class
                    , Kasbon.class, IsiKasbon.class},version = 1)
public abstract class TokoDatabase extends RoomDatabase {

    private static TokoDatabase instance;


    //Dao untuk proses produk + kategori
    //public abstract ProdukDao produkDao();
    public abstract KategoriDao kategoriDao();
    //public abstract KategoriProdukDao kategoriProdukDao();

    //Dao untuk proses transaksi + produk

    //Dao untuk proses kasbon + transaksi

    public static synchronized TokoDatabase getInstance(Context context){

        //dihapus setelah testing
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    TokoDatabase.class, "toko_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallBack)
                    .build();

        }

        return instance;

    }



    private static RoomDatabase.Callback roomCallBack = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            KategoriDao kategoriDao = instance.kategoriDao();

            ExecutorService executorService = Executors.newSingleThreadExecutor();
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    //untuk dihapus setelah tes
                    kategoriDao.insert(new Kategori("Mie","Indomie"));
                    kategoriDao.insert(new Kategori("Plastik","Plastik"));
                    kategoriDao.insert(new Kategori("Sabun","Plastik"));
                    kategoriDao.insert(new Kategori("AlatTulis","Plastik"));
                }
            });

        }
    };

}
