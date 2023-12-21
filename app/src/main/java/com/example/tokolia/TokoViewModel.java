package com.example.tokolia;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class TokoViewModel extends AndroidViewModel {

    private TokoRepository repository;
    private LiveData<List<Kategori>> kategoris;
    //private LiveData<List<Produk>> produks;

    public TokoViewModel(@NonNull Application application) {
        super(application);

        repository = new TokoRepository(application);
        kategoris = repository.getALlKategori();
        //produks = repository.getAllProduk();

    }

    //----------------------part Kategori---------------------------------------------------------->
    public void insertKategori(Kategori kategori){
        repository.insertKategori(kategori);
    }

    public void updateKategori(Kategori kategori){
        repository.updateKategori(kategori);
    }

    public void deleteKategori(Kategori kategori){
        repository.deleteKategori(kategori);
    }

    public LiveData<List<Kategori>> getAllKategori(){
        return kategoris;
    }

    //--------------------akhir bagian Kategori---------------------------------------------------->




    /*
    //--------------------part Produk-------------------------------------------------------------->
    public void insertProduk(Produk produk){
        repository.insertProduk(produk);
    }

    public void updateProduk(Produk produk){
        repository.updateProduk(produk);
    }

    public void deleteProduk(Produk produk){
        repository.deleteProduk(produk);
    }

    public LiveData<List<Produk>> getAllProduk(){
        return produks;
    }


    public LiveData<List<Produk>> getAllProdukOnKategori(String kategori){
        LiveData<List<Produk>> selectedProduk;

        selectedProduk = repository.getSelectedProduk(kategori);
        return selectedProduk;
    }
    //-------------------------akhir bagian produk------------------------------------------------->
    */
}
