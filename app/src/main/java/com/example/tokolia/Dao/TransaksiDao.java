package com.example.tokolia.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.example.tokolia.Entites.Transaksi;

import java.util.Date;
import java.util.List;

@Dao
public interface TransaksiDao {

    @Insert
    void insert(Transaksi transaksi);

    @Update
    void update(Transaksi transaksi);

    @Delete
    void delete(Transaksi transaksi);

    @Query("SELECT * FROM transaksi ORDER BY id_transaksi ASC")
    LiveData<List<Transaksi>> getAllTransaksi();

    @Transaction
    @Query("SELECT * FROM transaksi WHERE jenis_transaksi = :jenis")
    LiveData<List<Transaksi>> getAllTransaksiByJenis(String jenis);

    @Transaction
    @Query("SELECT * FROM transaksi WHERE kasbon = :pemilikKasbon")
    LiveData<List<Transaksi>> getAllTransaksiOnKasbon(String pemilikKasbon);

    @Transaction
    @Query("SELECT * FROM transaksi WHERE tanggal = :tanggal")
    LiveData<List<Transaksi>> getAllTransaksiOnTanggal(Date tanggal);
}
