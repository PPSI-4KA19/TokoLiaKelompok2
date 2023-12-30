package com.example.tokolia;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

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

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Kategori.class, Produk.class,
                    Transaksi.class, TransaksiProdukCrossRef.class,
                    Kasbon.class, Cart.class, Pencatatan.class},version = 1)
@TypeConverters({Converters.class})
public abstract class TokoDatabase extends RoomDatabase {

    private static TokoDatabase instance;


    public abstract KategoriDao kategoriDao();
    public abstract ProdukDao produkDao();
    public abstract TransaksiDao transaksiDao();
    public abstract KasbonDao kasbonDao();
    public abstract TransaksiProdukCrossDao transaksiProdukCrossDao();
    public abstract CartDao cartDao();
    public abstract PencatatanDao pencatatanDao();

    public static synchronized TokoDatabase getInstance(Context context){

        //dihapus setelah testing
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    TokoDatabase.class, "toko_database")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .addCallback(roomCallBack)
                    .build();

        }

        return instance;

    }



    private static RoomDatabase.Callback roomCallBack = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            KategoriDao kategoriDao = instance.kategoriDao();
            ProdukDao produkDao = instance.produkDao();
            TransaksiDao transaksiDao = instance.transaksiDao();
            KasbonDao kasbonDao = instance.kasbonDao();
            TransaksiProdukCrossDao transaksiProdukCrossDao = instance.transaksiProdukCrossDao();
            CartDao cartDao = instance.cartDao();
            PencatatanDao pencatatanDao = instance.pencatatanDao();

            ExecutorService executorService = Executors.newSingleThreadExecutor();
            executorService.execute(new Runnable() {
                @Override
                public void run() {

                    pencatatanDao.insertPencatatan(new Pencatatan("Pemasukan",0));
                    pencatatanDao.insertPencatatan(new Pencatatan("Pengeluaran",0));
                    pencatatanDao.insertPencatatan(new Pencatatan("Hutang",0));

                    /*//untuk dihapus setelah tes
                    //Kategori
                    kategoriDao.insert(new Kategori("Mie","Indomie"));
                    kategoriDao.insert(new Kategori("Plastik","Ya plastik"));
                    kategoriDao.insert(new Kategori("Sabun","Rinso, Lifeboy, dll"));
                    kategoriDao.insert(new Kategori("AlatTulis","Bolpen dan kawannya"));
                    //Produk
                    produkDao.insert(new Produk("Indomie Goreng",5000,3500,10,"Mie"));
                    produkDao.insert(new Produk("Mie Sedap Goreng",5000,3800,11,"Mie"));
                    produkDao.insert(new Produk("Mie Gaga Goreng",1500,200,13,"Mie"));
                    */

                    /*
                    //Kategori
                    kategoriDao.insert(new Kategori("Laris","paling laris"));
                    //Produk
                    produkDao.insert(new Produk("Hadalabo",25000,10000,4,"Laris"));
                    //Kasbon
                    kasbonDao.insert(new Kasbon("David",50000,50000));
                    //Transaksi
                    transaksiDao.insert(new Transaksi("20231228156487","28-12-2023","hutang","David"));
                    //CrossRef
                    transaksiProdukCrossDao.insert(new TransaksiProdukCrossRef("2023122815648",1,2,50000));
                    */
                }
            });

        }
    };

}
