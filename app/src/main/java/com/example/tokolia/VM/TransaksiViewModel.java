package com.example.tokolia.VM;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.tokolia.Entites.Produk;
import com.example.tokolia.Entites.Transaksi;
import com.example.tokolia.Entites.TransaksiProdukCrossRef;
import com.example.tokolia.TokoRepository;

import java.util.Date;
import java.util.List;

public class TransaksiViewModel extends AndroidViewModel {

    TokoRepository repository;

    private LiveData<List<TransaksiProdukCrossRef>> transaksiProduks;
    private LiveData<List<Transaksi>> transaksis;
    private LiveData<List<Produk>> produks;

    //public List<Produk> listProduk = (List<Produk>) produks;

    public TransaksiViewModel(@NonNull Application application) {
        super(application);

        repository = new TokoRepository(application);
        transaksis = repository.getAllTransaksi();
        transaksiProduks = repository.getAllTransaksiProduk();
        produks = repository.getAllProduk();
    }


    //-------------------------part transaksi entity----------------------------------------------->
    public void insertTransaksi(Transaksi transaksi){
        repository.insertTransaksi(transaksi);
    }

    public void deleteTransaksi(Transaksi transaksi){
        repository.deleteTransaksi(transaksi);
    }

    public void updateTransaksi(Transaksi transaksi){
        repository.updateTransaksi(transaksi);
    }

    public LiveData<List<Transaksi>> getAllTransaksi(){
        return transaksis;
    }

    private LiveData<List<Transaksi>> selectedTransaksis;
    public LiveData<List<Transaksi>> getTransaksiByDate(String date){
        selectedTransaksis = repository.getAllTransaksiOnTanggal(date);
        return selectedTransaksis;
    }

    public LiveData<List<Transaksi>> getTransaksiByJenis(String jenis){
        selectedTransaksis = repository.getAllTransaksiByJenis(jenis);
        return selectedTransaksis;
    }

    public LiveData<List<Transaksi>> getTransaksiByKasbon(String pemilik){
        selectedTransaksis = repository.getAllTransaksiOnKasbon(pemilik);
        return selectedTransaksis;
    }
    //-------------------------akhir part transaksi entity----------------------------------------->





    //------------------------part transaksiproduk------------------------------------------------->

    public void insertCrossRef(TransaksiProdukCrossRef transaksiProdukCrossRef){
        repository.insertTransaksiProduk(transaksiProdukCrossRef);
    }

    public void deleteCrossRef(TransaksiProdukCrossRef transaksiProdukCrossRef){
        repository.deleteTransaksiProduk(transaksiProdukCrossRef);
    }

    public void updateCrossRef(TransaksiProdukCrossRef transaksiProdukCrossRef){
        repository.updateTransaksiProduk(transaksiProdukCrossRef);
    }

    public LiveData<List<TransaksiProdukCrossRef>> getAllCrossRefs(){
        return transaksiProduks;
    }

    public LiveData<List<TransaksiProdukCrossRef>> getAllCrossRefsById(String id){
        LiveData<List<TransaksiProdukCrossRef>> selectedTransaksiProduks =
                repository.getAllTransaksiProdukSpesifikTransaksi(id);
        return selectedTransaksiProduks;
    }

    //-----------------------akhir part transaksiproduk-------------------------------------------->





    //----------------------------part produk------------------------------------------------------>

    private LiveData<List<Produk>> selectedProduks;
    public void updateProduk(Produk produk){
        repository.updateProduk(produk);
    }

    public LiveData<List<Produk>> getListProdukById(int idProduk){
        selectedProduks = repository.getListProdukById(idProduk);
        return selectedProduks;
    }

    public LiveData<List<Produk>> getAllProduk(){
        return produks;
    }

}
