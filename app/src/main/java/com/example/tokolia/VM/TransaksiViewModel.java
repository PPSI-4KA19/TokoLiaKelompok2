package com.example.tokolia.VM;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.tokolia.Entites.Transaksi;
import com.example.tokolia.Entites.TransaksiProdukCrossRef;
import com.example.tokolia.TokoRepository;

import java.util.List;

public class TransaksiViewModel extends AndroidViewModel {

    TokoRepository repository;

    private LiveData<List<TransaksiProdukCrossRef>> transaksiProduks;
    private LiveData<List<Transaksi>> transaksis;

    public TransaksiViewModel(@NonNull Application application) {
        super(application);

        repository = new TokoRepository(application);
        transaksis = repository.getAllTransaksi();
    }
}
