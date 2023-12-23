package com.example.tokolia.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.tokolia.Adapter.SelectProdukAdapter;
import com.example.tokolia.Produk;
import com.example.tokolia.ProdukViewModel;
import com.example.tokolia.R;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.List;

public class TransaksiBukaProdukActivity extends AppCompatActivity {

    SearchView search;
    MaterialToolbar toolbar;

    RecyclerView recyclerView;
    ProdukViewModel produkViewModel;

    String namaKategori;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaksi_buka_produk);

        //setup elemen
        search = findViewById(R.id.searchSelectProduk);
        toolbar = findViewById(R.id.toolbarSelectProduk);
        recyclerView = findViewById(R.id.recyclerSelectProduk);

        getData();


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        //-------------------------set recycler view----------------------------------------------->

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        SelectProdukAdapter adapter = new SelectProdukAdapter();
        recyclerView.setAdapter(adapter);

        produkViewModel = new ViewModelProvider.AndroidViewModelFactory(getApplication())
                .create(ProdukViewModel.class);
        produkViewModel.getAllProdukOnKategori(namaKategori)
                .observe(this, new Observer<List<Produk>>() {
            @Override
            public void onChanged(List<Produk> produks) {
                adapter.setProduks(produks);
            }
        });

        //--------------------------akhir set recycler view---------------------------------------->





        //-----------------------------click on produk--------------------------------------------->

        adapter.setOnItemClickListener(new SelectProdukAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Produk produk) {

                Intent select = new Intent(TransaksiBukaProdukActivity.this, TransaksiActivity.class);
                Toast.makeText(getApplicationContext(),
                        "Produk berhasil ditambahkan",Toast.LENGTH_SHORT).show();

            }
        });
        //----------------------------akhir click on produk---------------------------------------->


    }

    public void getData(){
        Intent i = getIntent();
        namaKategori = i.getStringExtra("namaKategori");

        toolbar.setTitle(namaKategori);
    }
}