package com.example.tokolia;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Kategori.class,Produk.class},version = 1)
public abstract class TokoDatabase extends RoomDatabase {

    private static TokoDatabase instance;


    public abstract KategoriDao kategoriDao();
    public abstract ProdukDao produkDao();


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
            ProdukDao produkDao = instance.produkDao();

            ExecutorService executorService = Executors.newSingleThreadExecutor();
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    //untuk dihapus setelah tes
                    //Kategori
                    kategoriDao.insert(new Kategori("Mie","Indomie"));
                    kategoriDao.insert(new Kategori("Plastik","Ya plastik"));
                    kategoriDao.insert(new Kategori("Sabun","Rinso, Lifeboy, dll"));
                    kategoriDao.insert(new Kategori("AlatTulis","Bolpen dan kawannya"));
                    //Produk
                    produkDao.insert(new Produk("Indomie Goreng",5000,3500,10,"Mie"));
                    produkDao.insert(new Produk("Mie Sedap Goreng",5000,3800,11,"Mie"));
                    produkDao.insert(new Produk("Mie Gaga Goreng",1500,200,13,"Mie"));


                }
            });

        }
    };

}
