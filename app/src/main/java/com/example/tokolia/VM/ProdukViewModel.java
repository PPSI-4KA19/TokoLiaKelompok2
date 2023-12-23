package com.example.tokolia.VM;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.tokolia.Entites.Produk;
import com.example.tokolia.TokoRepository;

import java.util.List;

public class ProdukViewModel extends AndroidViewModel {

    private TokoRepository repository;
    private LiveData<List<Produk>> produks;
    public ProdukViewModel(@NonNull Application application) {
        super(application);

        repository = new TokoRepository(application);
        produks = repository.getAllProduk();
    }

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

    public void deleteProdukOnKategori(String kategori){
        repository.deleteProdukOnKategori(kategori);
    }

}
