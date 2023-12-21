package com.example.tokolia;

import androidx.activity.OnBackPressedCallback;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;

import com.google.android.material.appbar.MaterialToolbar;

import java.util.List;

public class ListProduk extends AppCompatActivity {

    Button addProduk;
    MaterialToolbar toolbar;

    private ProdukViewModel produkViewModel;
    ActivityResultLauncher<Intent> activityResultLauncherOpenProduk;

    //containers untuk nyimpan message intent
    String namaKategori;
    String descKategori;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_produk);

        toolbar = findViewById(R.id.listProdukToolbar);

        Intent i = getIntent();
        namaKategori = i.getStringExtra("kategori");
        descKategori = i.getStringExtra("deskripsi");
        toolbar.setTitle(namaKategori);

        //getData();

        RecyclerView recyclerProduk = findViewById(R.id.recyclerProduk);
        recyclerProduk.setLayoutManager(new LinearLayoutManager(this));

        ProdukAdapter adapter = new ProdukAdapter();
        recyclerProduk.setAdapter(adapter);


        produkViewModel = new ViewModelProvider.AndroidViewModelFactory(getApplication())
                .create(ProdukViewModel.class);
        produkViewModel.getAllProdukOnKategori(namaKategori).observe(this, new Observer<List<Produk>>() {
            @Override
            public void onChanged(List<Produk> produks) {
                //update Recycler View
                adapter.setProduks(produks);
            }
        });


        //-------------------tombol tambah produk-------------------------------------------------->

        addProduk = findViewById(R.id.buttonTambahProduk);
        addProduk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ke activity tambah produk - isi field
                Intent toAddProduk = new Intent(ListProduk.this, TambahProduk.class);
                startActivity(toAddProduk);
                finish();


            }
        });

        //-------------------akhir tombol tambah produk-------------------------------------------->

        //backpress
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                Intent backToKategori = new Intent(ListProduk.this, produkkategori.class);
                startActivity(backToKategori);
                finish();
            }
        });

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backToKategori = new Intent(ListProduk.this, produkkategori.class);
                startActivity(backToKategori);
                finish();
            }
        });

    }

    //method untuk terima intent buka produk awal


    public void getData(){
        Intent i = getIntent();
        namaKategori = i.getStringExtra("kategori");
        descKategori = i.getStringExtra("deskripsi");

        //toolbar.setTitle(namaKategori);
    }


    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_search, menu);

        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Ketik untuk mencari");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {


                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }*/


}