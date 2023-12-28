package com.example.tokolia.VM;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.tokolia.Entites.Kasbon;
import com.example.tokolia.Entites.Transaksi;
import com.example.tokolia.Entites.TransaksiProdukCrossRef;
import com.example.tokolia.TokoRepository;

import java.util.List;

public class KasbonViewModel extends AndroidViewModel {

    private LiveData<List<Kasbon>> kasbons;

    private LiveData<List<Transaksi>> transaksis;

    private LiveData<List<TransaksiProdukCrossRef>> crossrefs;

    TokoRepository repository;


    public KasbonViewModel(@NonNull Application application) {
        super(application);

        repository = new TokoRepository(application);
        kasbons = repository.getAllKasbon();
        transaksis = repository.getAllTransaksi();
        crossrefs = repository.getAllTransaksiProduk();

    }

    //-------------------------------kasbon-------------------------------------------------------->

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

    //---------------------------akhir kasbon------------------------------------------------------>



    //------------------------------transaksi------------------------------------------------------>

    public LiveData<List<Transaksi>> getAllTransaksi(){
        return transaksis;
    }

    LiveData<List<Transaksi>> selectedTransaksis;
    public LiveData<List<Transaksi>> getTransaksisOnKasbon(String namaKasbon){
        selectedTransaksis = repository.getAllTransaksiOnKasbon(namaKasbon);
        return selectedTransaksis;
    }

    public void updateTransaksi(Transaksi transaksi){
        repository.updateTransaksi(transaksi);
    }

    //----------------------------transaksi kasbon------------------------------------------------->



    //--------------------------------crossrefs---------------------------------------------------->

    public LiveData<List<TransaksiProdukCrossRef>> getAllCrossRefs(){
        return crossrefs;
    }

    //-------------------------------akhir crossrefs----------------------------------------------->
}
