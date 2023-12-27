package com.example.tokolia.VM;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.tokolia.Entites.Cart;
import com.example.tokolia.Entites.Kasbon;
import com.example.tokolia.Entites.Produk;
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

    private LiveData<List<Kasbon>> kasbons;


    public CartViewModel(@NonNull Application application) {
        super(application);

        repository = new TokoRepository(application);
        carts = repository.getAllCartInfo();
        transaksis = repository.getAllTransaksi();
        crossref = repository.getAllTransaksiProduk();
        kasbons = repository.getAllKasbon();

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
        return crossref;
    }

    public LiveData<List<TransaksiProdukCrossRef>> getAllCrossRefsById(String id){
        LiveData<List<TransaksiProdukCrossRef>> selectedTransaksiProduks =
                repository.getAllTransaksiProdukSpesifikTransaksi(id);
        return selectedTransaksiProduks;
    }

    //-----------------------akhir part transaksiproduk-------------------------------------------->





    //----------------------------------part produk------------------------------------------------>

    public void increaseProdukStok(int idProduk, int jumlah){
        repository.increaseProdukStok(idProduk, jumlah);
    }

    public void decreaseProdukStok(int idProduk, int jumlah){
        repository.decreaseProdukStok(idProduk, jumlah);
    }

    //---------------------------------akhir part produk------------------------------------------->




    //----------------------------------part kasbon------------------------------------------------>

    public LiveData<List<Kasbon>> getAllKasbon(){
        return kasbons;
    }

    public void insertKasbon(Kasbon kasbon){
        repository.insertKasbon(kasbon);
    }

    public void deleteKasbon(Kasbon kasbon){
        repository.deleteKasbon(kasbon);
    }

    public void updateKasbon(Kasbon kasbon){
        repository.updateKasbon(kasbon);
    }

    //--------------------------------akhir part kasbon-------------------------------------------->
}
