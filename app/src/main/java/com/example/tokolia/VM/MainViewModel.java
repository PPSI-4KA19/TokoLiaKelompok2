package com.example.tokolia.VM;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.tokolia.Entites.Pencatatan;
import com.example.tokolia.Entites.Produk;
import com.example.tokolia.Entites.Transaksi;
import com.example.tokolia.Entites.TransaksiProdukCrossRef;
import com.example.tokolia.TokoRepository;

import java.util.List;

public class MainViewModel extends AndroidViewModel {

    TokoRepository repository;
    LiveData<List<Pencatatan>> index;
    LiveData<List<Transaksi>> transaksis;
    LiveData<List<TransaksiProdukCrossRef>> crossrefs;
    LiveData<List<Produk>> produks;

    public MainViewModel(@NonNull Application application) {
        super(application);

        repository = new TokoRepository(application);
        index = repository.getAllPencatatan();
        transaksis = repository.getAllTransaksi();
        crossrefs = repository.getAllTransaksiProduk();
        produks = repository.getAllProduk();
    }


    //----------------------------pencatatan------------------------------------------------------->
    public LiveData<List<Pencatatan>> getAllIndex(){
        return index;
    }
    //----------------------------akhir pencatatan------------------------------------------------->





    //-----------------------------transaksi------------------------------------------------------->
    public LiveData<List<Transaksi>> getAllTransaksi(){
        return transaksis;
    }

    public LiveData<List<Transaksi>> getAllTransaksiByJenis(String jenis){
        LiveData<List<Transaksi>> selectedJenis = repository.getAllTransaksiByJenis(jenis);
        return selectedJenis;
    }
    //-----------------------------akhir transaksi------------------------------------------------->





    //------------------------------crossrefs------------------------------------------------------>
    public LiveData<List<TransaksiProdukCrossRef>> getAllCrossRefs(){
        return crossrefs;
    }
    //------------------------------akhir crossrefs------------------------------------------------>





    //--------------------------------produks------------------------------------------------------>
    public LiveData<List<Produk>> getAllProduks(){
        return produks;
    }
    //-------------------------------akhir produks------------------------------------------------->
}
