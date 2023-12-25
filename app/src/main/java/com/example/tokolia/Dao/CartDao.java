package com.example.tokolia.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.example.tokolia.Entites.Cart;

import java.util.List;

@Dao
public interface CartDao {

    @Insert
    public void insertCart(Cart cart);
    @Delete
    public void deleteCart(Cart cart);
    @Update
    public void updateCart(Cart cart);

    @Transaction
    @Query("SELECT * FROM cart")
    public LiveData<List<Cart>> getALlCartInfo();

    @Transaction
    @Query("DELETE FROM cart")
    public void cleanCart();

}
