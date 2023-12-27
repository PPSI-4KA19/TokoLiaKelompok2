package com.example.tokolia.VM;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.tokolia.Entites.Kasbon;
import com.example.tokolia.TokoRepository;

import java.util.List;

public class KasbonViewModel extends AndroidViewModel {

    private LiveData<List<Kasbon>> kasbons;

    TokoRepository repository;


    public KasbonViewModel(@NonNull Application application) {
        super(application);

        repository = new TokoRepository(application);
        kasbons = repository.getAllKasbon();

    }

    public void insertKasbon(Kasbon kasbon){
        repository.insertKasbon(kasbon);
    }

    public void updateKasbon(Kasbon kasbon){
        repository.updateKasbon(kasbon);
    }

    public void deleteKasbon(Kasbon kasbon){
        repository.updateKasbon(kasbon);
    }

    public LiveData<List<Kasbon>> getAllKasbons(){
        return kasbons;
    }


}
