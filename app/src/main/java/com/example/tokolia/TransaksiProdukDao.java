package com.example.tokolia;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TransaksiProdukDao {

    @Insert
    void insert(TransaksiProduk transaksiProduk);

    @Update
    void update(TransaksiProduk transaksiProduk);

    @Delete
    void delete(TransaksiProduk transaksiProduk);

    @Query("SELECT * from transaksi_produk ORDER BY id_transaksi ASC")
    LiveData<List<TransaksiProduk>> getAllTransaksiProduk();

}
