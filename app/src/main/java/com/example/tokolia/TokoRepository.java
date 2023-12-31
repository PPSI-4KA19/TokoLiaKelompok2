package com.example.tokolia;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.tokolia.Dao.CartDao;
import com.example.tokolia.Dao.KasbonDao;
import com.example.tokolia.Dao.KategoriDao;
import com.example.tokolia.Dao.PencatatanDao;
import com.example.tokolia.Dao.ProdukDao;
import com.example.tokolia.Dao.TransaksiDao;
import com.example.tokolia.Dao.TransaksiProdukCrossDao;
import com.example.tokolia.Entites.Cart;
import com.example.tokolia.Entites.Kasbon;
import com.example.tokolia.Entites.Kategori;
import com.example.tokolia.Entites.Pencatatan;
import com.example.tokolia.Entites.Produk;
import com.example.tokolia.Entites.Transaksi;
import com.example.tokolia.Entites.TransaksiProdukCrossRef;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TokoRepository {

    private KategoriDao kategoriDao;
    private ProdukDao produkDao;
    private TransaksiDao transaksiDao;
    private TransaksiProdukCrossDao transaksiProdukCrossDao;
    private KasbonDao kasbonDao;
    private CartDao cartDao;
    private PencatatanDao pencatatanDao;

    private LiveData<List<Kategori>> kategoris;
    private LiveData<List<Produk>> produks;
    private LiveData<List<Transaksi>> transaksis;
    private LiveData<List<TransaksiProdukCrossRef>> transaksiProdukCrossRefs;
    private LiveData<List<Kasbon>> kasbons;
    private LiveData<List<Cart>> carts;
    private LiveData<List<Pencatatan>> pencatatans;


    ExecutorService executors = Executors.newSingleThreadExecutor();

    public TokoRepository(Application application){

        TokoDatabase database = TokoDatabase.getInstance(application);
        //----------------------------kategori----------------------------------------------------->
        kategoriDao = database.kategoriDao();
        kategoris = kategoriDao.getAllKategori();
        //----------------------------produk------------------------------------------------------->
        produkDao = database.produkDao();
        produks = produkDao.getAllProduk();
        //----------------------------transaksi---------------------------------------------------->
        transaksiDao = database.transaksiDao();
        transaksis = transaksiDao.getAllTransaksi();
        //----------------------------crossrefs---------------------------------------------------->
        transaksiProdukCrossDao = database.transaksiProdukCrossDao();
        transaksiProdukCrossRefs = transaksiProdukCrossDao.getAllTransaksiProdukCrossRef();
        //-----------------------------kasbon------------------------------------------------------>
        kasbonDao = database.kasbonDao();
        kasbons = kasbonDao.getAllKasbon();




        //------------------------------cart------------------------------------------------------->
        cartDao = database.cartDao();
        carts = cartDao.getALlCartInfo();
        //----------------------------pencatatan--------------------------------------------------->
        pencatatanDao = database.pencatatanDao();
        pencatatans = pencatatanDao.getAllPencatatan();

    }


    //-----------------------------TRANSAKSI------------------------------------------------------->

    public void insertTransaksi(Transaksi transaksi){
        executors.execute(new Runnable() {
            @Override
            public void run() {
                transaksiDao.insert(transaksi);
            }
        });
    }

    public void updateTransaksi(Transaksi transaksi){
        executors.execute(new Runnable() {
            @Override
            public void run() {
                transaksiDao.update(transaksi);
            }
        });
    }

    public void deleteTransaksi(Transaksi transaksi){
        executors.execute(new Runnable() {
            @Override
            public void run() {
                transaksiDao.delete(transaksi);
            }
        });
    }



    public LiveData<List<Transaksi>> getAllTransaksi(){
        return transaksis;
    }

    //extra queries
    LiveData<List<Transaksi>> selectedTransaksis;

    public LiveData<List<Transaksi>> getAllTransaksiByJenis(String jenis){
        selectedTransaksis = transaksiDao.getAllTransaksiByJenis(jenis);
        return selectedTransaksis;
    }

    public LiveData<List<Transaksi>> getAllTransaksiOnKasbon(String namaPemilik){
        selectedTransaksis = transaksiDao.getAllTransaksiOnKasbon(namaPemilik);
        return selectedTransaksis;
    }

    public LiveData<List<Transaksi>> getAllTransaksiOnTanggal(String tanggal){
        selectedTransaksis = transaksiDao.getAllTransaksiOnTanggal(tanggal);
        return selectedTransaksis;
    }

    //----------------------------AKHIR TRANSAKSI-------------------------------------------------->





    //------------------------TRANSAKSI PRODUK (CROSSREF)------------------------------------------>

    public void insertTransaksiProduk(TransaksiProdukCrossRef transaksiProdukCrossRef){
        executors.execute(new Runnable() {
            @Override
            public void run() {
                transaksiProdukCrossDao.insert(transaksiProdukCrossRef);
            }
        });
    }

    public void updateTransaksiProduk(TransaksiProdukCrossRef transaksiProdukCrossRef){
        executors.execute(new Runnable() {
            @Override
            public void run() {
                transaksiProdukCrossDao.update(transaksiProdukCrossRef);
            }
        });
    }

    public void deleteTransaksiProduk(TransaksiProdukCrossRef transaksiProdukCrossRef){
        executors.execute(new Runnable() {
            @Override
            public void run() {
                transaksiProdukCrossDao.delete(transaksiProdukCrossRef);
            }
        });
    }

    public  LiveData<List<TransaksiProdukCrossRef>> getAllTransaksiProduk(){
        return transaksiProdukCrossRefs;
    }

    public  LiveData<List<TransaksiProdukCrossRef>> getAllTransaksiProdukSpesifikTransaksi(String id_transaksi){
        LiveData<List<TransaksiProdukCrossRef>> selectedCrossrefs
                = transaksiProdukCrossDao.getAllTransaksiProdukSpesifikTransaksi(id_transaksi);
        return selectedCrossrefs;
    }

    public List<TransaksiProdukCrossRef> getListCrossRefById(String id){
        return transaksiProdukCrossDao.getListCrossRefById(id);
    }
    //-------------------------AKHIR TRANSAKSI PRODUK (CROSSREFF)---------------------------------->





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

    public LiveData<List<Produk>> getListProdukById(int idProduk){
        return produkDao.getListProdukById(idProduk);
    }

    public void decreaseProdukStok(int idProduk, int jumlah){
        executors.execute(new Runnable() {
            @Override
            public void run() {
                produkDao.decreaseProdukStok(idProduk,jumlah);
            }
        });
    }

    public void increaseProdukStok(int idProduk, int jumlah){
        executors.execute(new Runnable() {
            @Override
            public void run() {
                produkDao.increaseProdukStok(idProduk,jumlah);
            }
        });
    }

    public List<Produk> getListSpesifikProduk(int id){
        return produkDao.getProduksById(id);
    }

    //---------------------------AKHIR PRODUK------------------------------------------------------>





    //-----------------------------CARTS----------------------------------------------------------->

    public void insertCart(Cart cart){
        executors.execute(new Runnable() {
            @Override
            public void run() {
                cartDao.insertCart(cart);
            }
        });
    }

    public void updateCart(Cart cart){
        executors.execute(new Runnable() {
            @Override
            public void run() {
                cartDao.updateCart(cart);
            }
        });
    }

    public void deleteCart(Cart cart){
        executors.execute(new Runnable() {
            @Override
            public void run() {
                cartDao.deleteCart(cart);
            }
        });
    }

    public LiveData<List<Cart>> getAllCartInfo(){
        return carts;
    }

    public void clearCart(){
        executors.execute(new Runnable() {
            @Override
            public void run() {
                cartDao.cleanCart();
            }
        });
    }

    //---------------------------AKHIR CARTS------------------------------------------------------->





    //------------------------------PENCATATAN----------------------------------------------------->

    public void insertPencatatan(Pencatatan pencatatan){
        executors.execute(new Runnable() {
            @Override
            public void run() {
                pencatatanDao.insertPencatatan(pencatatan);
            }
        });
    }

    public void updatePencatatan(Pencatatan pencatatan){
        executors.execute(new Runnable() {
            @Override
            public void run() {
                pencatatanDao.updatePencatatan(pencatatan);
            }
        });
    }

    public LiveData<List<Pencatatan>> getAllPencatatan(){
        return pencatatans;
    }

    //-----------------------------AKHIR PENCATATAN------------------------------------------------>

    //------------------------------part KASBON---------------------------------------------------->

    public LiveData<List<Kasbon>> getAllKasbon(){
        return kasbons;
    }

    public void updateKasbon(Kasbon kasbon){
        executors.execute(new Runnable() {
            @Override
            public void run() {
                kasbonDao.update(kasbon);
            }
        });
    }

    public void insertKasbon(Kasbon kasbon){
        executors.execute(new Runnable() {
            @Override
            public void run() {
                kasbonDao.insert(kasbon);
            }
        });
    }

    public void deleteKasbon(Kasbon kasbon){
        executors.execute(new Runnable() {
            @Override
            public void run() {
                kasbonDao.delete(kasbon);
            }
        });
    }

    //---------------------------akhir part KASBON------------------------------------------------->
}
