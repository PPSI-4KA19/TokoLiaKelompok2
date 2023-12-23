package com.example.tokolia.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.tokolia.Entites.Kategori;
import com.example.tokolia.Adapter.KategoriAdapter;
import com.example.tokolia.VM.KategoriViewModel;
import com.example.tokolia.R;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.List;

public class TransaksiBukaKategoriActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    MaterialToolbar toolbar;


    private KategoriViewModel kategoriViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaksi_buka_kategori);


        recyclerView = findViewById(R.id.recyclerTransaksiKategori);
        toolbar = findViewById(R.id.toolbarTransaksiKategori);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        //------------------------------recycler view---------------------------------------------->

        recyclerView.setLayoutManager(new GridLayoutManager(this,2));

        KategoriAdapter kategoriAdapter = new KategoriAdapter();
        recyclerView.setAdapter(kategoriAdapter);

        kategoriViewModel = new ViewModelProvider.AndroidViewModelFactory(getApplication())
                .create(KategoriViewModel.class);
        kategoriViewModel.getAllKategori().observe(this, new Observer<List<Kategori>>() {
            @Override
            public void onChanged(List<Kategori> kategoris) {
                kategoriAdapter.setKategoris(kategoris);
            }
        });

        //-------------------------akhri recycler view--------------------------------------------->




        //------------------------open produk dari klik recycler----------------------------------->

        kategoriAdapter.setOnItemClickListener(new KategoriAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Kategori kategori) {
                Intent toProduk = new Intent(TransaksiBukaKategoriActivity.this,
                        TransaksiBukaProdukActivity.class);
                toProduk.putExtra("namaKategori",kategori.getNamakategori());
                startActivity(toProduk);
            }
        });

        //---------------------akhir open produk dari klik recycler-------------------------------->


    }
}