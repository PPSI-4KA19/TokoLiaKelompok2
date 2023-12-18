package com.example.tokolia;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

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
}
