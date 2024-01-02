package com.example.tokolia.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.tokolia.Adapter.SelectProdukAdapter;
import com.example.tokolia.Entites.Produk;
import com.example.tokolia.VM.ProdukViewModel;
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

                if(produk.getStok()>0) {
                    Intent select = new Intent(TransaksiBukaProdukActivity.this, TransaksiActivity.class);
                    select.putExtra("id_produk", produk.getId_produk());
                    select.putExtra("nama_produk", produk.getNama_produk());
                    select.putExtra("harga_produk", produk.getHarga_jual());
                    select.putExtra("modal_produk", produk.getHarga_modal());
                    select.putExtra("stok_produk", produk.getStok());
                    select.putExtra("kategori_produk", produk.getKategori());
                    startActivity(select);
                    finish();
                } else {
                    AlertDialog.Builder build = new AlertDialog.Builder(TransaksiBukaProdukActivity.this);
                    build.setTitle("Stok 0");
                    build.setMessage("Barang habis");
                    build.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    build.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    AlertDialog alert = build.create();
                    alert.show();
                }
            }
        });
        //----------------------------akhir click on produk---------------------------------------->





        //------------------------------search view------------------------------------------------>

        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });

        //-----------------------------akhir search view------------------------------------------->

    }

    public void getData(){
        Intent i = getIntent();
        namaKategori = i.getStringExtra("namaKategori");

        toolbar.setTitle(namaKategori);
    }
}