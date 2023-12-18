package com.example.tokolia;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TokoRepository {

    private KategoriDao kategoriDao;

    private LiveData<List<Kategori>> kategoris;


    ExecutorService executors = Executors.newSingleThreadExecutor();

    public TokoRepository(Application application){

        TokoDatabase database = TokoDatabase.getInstance(application);
        kategoriDao = database.kategoriDao();
        kategoris = kategoriDao.getAllKategori();

    }

    public void insertKategori(Kategori kategori){
        executors.execute(new Runnable() {
            @Override
            public void run() {
                kategoriDao.insert(kategori);
            }
        });

    }

    public void updateKategori(Kategori kategori){
        executors.execute(new Runnable() {
            @Override
            public void run() {
                kategoriDao.update(kategori);
            }
        });
    }

    public void deleteKategori(Kategori kategori){
        executors.execute(new Runnable() {
            @Override
            public void run() {
                kategoriDao.delete(kategori);
            }
        });
    }

    public LiveData<List<Kategori>> getALlKategori(){
        return kategoris;
    }



}
