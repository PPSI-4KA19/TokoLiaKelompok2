package com.example.tokolia;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class TokoViewModel extends AndroidViewModel {

    private TokoRepository repository;
    private LiveData<List<Kategori>> kategoris;

    public TokoViewModel(@NonNull Application application) {
        super(application);

        repository = new TokoRepository(application);
        kategoris = repository.getALlKategori();

    }

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

}
