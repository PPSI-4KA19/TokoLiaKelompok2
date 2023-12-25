package com.example.tokolia.VM;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.tokolia.Entites.Cart;
import com.example.tokolia.Entites.Transaksi;
import com.example.tokolia.Entites.TransaksiProdukCrossRef;
import com.example.tokolia.TokoRepository;

import java.util.Date;
import java.util.List;

public class CartViewModel extends AndroidViewModel {

    LiveData<List<Cart>> carts;
    TokoRepository repository;

    private LiveData<List<TransaksiProdukCrossRef>> crossref;
    private LiveData<List<Transaksi>> transaksis;


    public CartViewModel(@NonNull Application application) {
        super(application);

        repository = new TokoRepository(application);
        carts = repository.getAllCartInfo();
        transaksis = repository.getAllTransaksi();
        crossref = repository.getAllTransaksiProduk();

    }


    //--------------------------part carts view---------------------------------------------------->

    public void insertCart(Cart cart){
        repository.insertCart(cart);
    }

    public void updateCart(Cart cart){
        repository.updateCart(cart);
    }

    public void deleteCart(Cart cart){
        repository.deleteCart(cart);
    }

    public void clearCart(){
        repository.clearCart();
    }

    public LiveData<List<Cart>> getAllCartInfo(){
        return carts;
    }

    //--------------------------akhir part carts view---------------------------------------------->



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
    public LiveData<List<Transaksi>> getTransaksiByDate(Date date){
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
        return crossref;
    }

    public LiveData<List<TransaksiProdukCrossRef>> getAllCrossRefsById(int id){
        LiveData<List<TransaksiProdukCrossRef>> selectedTransaksiProduks =
                repository.getAllTransaksiProdukSpesifikTransaksi(id);
        return selectedTransaksiProduks;
    }

    //-----------------------akhir part transaksiproduk-------------------------------------------->

}
