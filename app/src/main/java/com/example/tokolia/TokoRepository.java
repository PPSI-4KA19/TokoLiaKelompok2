package com.example.tokolia;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TokoRepository {

    private KategoriDao kategoriDao;
    private ProdukDao produkDao;

    private LiveData<List<Kategori>> kategoris;
    private LiveData<List<Produk>> produks;


    ExecutorService executors = Executors.newSingleThreadExecutor();

    public TokoRepository(Application application){

        TokoDatabase database = TokoDatabase.getInstance(application);
        //----------------------------kategori----------------------------------------------------->
        kategoriDao = database.kategoriDao();
        kategoris = kategoriDao.getAllKategori();
        //----------------------------produk------------------------------------------------------->
        produkDao = database.produkDao();
        produks = produkDao.getAllProduk();

    }

    //-----------------------------KATEGORI-------------------------------------------------------->
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

    public void deleteKategoriSpesifik(String namaKategori){
        executors.execute(new Runnable() {
            @Override
            public void run() {
                kategoriDao.deleteKategoriSpesifik(namaKategori);
            }
        });
    }

    //------------------------AKHIR KATEGORI------------------------------------------------------->





    //------------------------PRODUK--------------------------------------------------------------->
    private LiveData<List<Produk>> selectedProduk;

    public void insertProduk(Produk produk){
        executors.execute(new Runnable() {
            @Override
            public void run() {
                produkDao.insert(produk);
            }
        });
    }

   public void updateProduk(Produk produk){
        executors.execute(new Runnable() {
            @Override
            public void run() {
                produkDao.update(produk);
            }
        });
    }

    public void deleteProduk(Produk produk){
        executors.execute(new Runnable() {
            @Override
            public void run() {
                produkDao.delete(produk);
            }
        });
    }

    public LiveData<List<Produk>> getSelectedProduk(String kategori){
        selectedProduk = produkDao.getSelectedProdukOnKategori(kategori);
        return selectedProduk;
    }

    public LiveData<List<Produk>> getAllProduk(){
        return produks;
    }

    public void deleteProdukOnKategori(String kategori){
        executors.execute(new Runnable() {
            @Override
            public void run() {
                produkDao.deleteAllProdukOnKategori(kategori);
            }
        });
    }


    //---------------------------AKHIR PRODUK------------------------------------------------------>


}
