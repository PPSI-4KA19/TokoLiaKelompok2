package com.example.tokolia.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.example.tokolia.Entites.TransaksiProdukCrossRef;

import java.util.List;

@Dao
public interface TransaksiProdukCrossDao {

    @Insert
    void insert(TransaksiProdukCrossRef transaksiProdukCrossRef);

    @Update
    void update(TransaksiProdukCrossRef transaksiProdukCrossRef);

    @Delete
    void delete(TransaksiProdukCrossRef transaksiProdukCrossRef);

    @Transaction
    @Query("SELECT * FROM crossref ORDER BY id_transaksi ASC")
    LiveData<List<TransaksiProdukCrossRef>> getAllTransaksiProdukCrossRef();

    @Transaction
    @Query("SELECT * FROM crossref WHERE id_transaksi = :id_transaksi ORDER BY id_transaksi ASC")
    LiveData<List<TransaksiProdukCrossRef>> getAllTransaksiProdukSpesifikTransaksi(int id_transaksi);
}
