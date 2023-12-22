package com.example.tokolia;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;

import java.util.List;

public class RestokListProduk extends AppCompatActivity {

    RecyclerView produkRecycler;
    MaterialToolbar toolbar;
    ProdukRestokAdapter adapter;
    private ProdukViewModel produkViewModel;

    //container penyimpan intent
    String namaKategori;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restok_list_produk);

        //setup elemen
        toolbar = findViewById(R.id.listRestokProdukToolbar);
        produkRecycler = findViewById(R.id.recyclerRestokProduk);
        produkRecycler.setLayoutManager(new LinearLayoutManager(this));


        getData();
        toolbar.setTitle(namaKategori);


        adapter = new ProdukRestokAdapter();
        produkRecycler.setAdapter(adapter);

        produkViewModel = new ViewModelProvider.AndroidViewModelFactory(getApplication())
                .create(ProdukViewModel.class);
        produkViewModel.getAllProdukOnKategori(namaKategori).observe(this, new Observer<List<Produk>>() {
            @Override
            public void onChanged(List<Produk> produks) {
                //update Recycler View
                adapter.setProduks(produks);
            }
        });





        //--------------------------tombol restok-------------------------------------------------->
        //CEK INI KALO ERROR
        adapter.setOnItemClickListener(new ProdukRestokAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Produk produk) {
                AlertDialog.Builder builder = new AlertDialog.Builder(RestokListProduk.this);
                View mView = getLayoutInflater().inflate(R.layout.layout_custom_dialog,null);

                EditText inputStok = (EditText) mView.findViewById(R.id.editRestokJumlah);
                EditText inputModal = (EditText) mView.findViewById(R.id.editRestokModal);
                Button buttonRestok = (Button) mView.findViewById(R.id.buttonSimpanRestok);

                //kosmetik
                inputStok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        inputStok.setBackground(AppCompatResources.getDrawable(getApplicationContext(),
                                R.drawable.background_alert));
                    }
                });

                builder.setView(mView);

                AlertDialog alertDialog = builder.create();

                int idProduk = produk.getId_produk();
                String namaProduk = produk.getNama_produk();
                int hargaJual = produk.getHarga_jual();
                int hargaModal = produk.getHarga_modal();
                int jumStok = produk.getStok();

                int newModal = 0;
                int newStok = 0;

                buttonRestok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(inputStok.getText().toString().equals("")||inputStok.getText() == null){

                            //start statement
                            inputStok.setBackground(AppCompatResources.getDrawable(getApplicationContext()
                                    ,R.drawable.background_alert));
                            inputStok.requestFocus();

                        } else if(inputModal.getText().toString().equals("")||
                            inputModal.getText() == null ||
                                Integer.parseInt(inputModal.getText().toString()) == 0){

                            //start statement

                            Toast.makeText(getApplicationContext(),"modal kosong",Toast.LENGTH_SHORT).show();
                        } else {

                            Toast.makeText(getApplicationContext(),"Data restok ditambahkan",Toast.LENGTH_SHORT).show();

                        }
                    }
                });
                alertDialog.show();

            }
        });

        //---------------------------akhir tombol restok------------------------------------------->





        //----------------all backpress (pencet back hp ataupun back di kiri atas)----------------->
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                Intent backToKategori = new Intent(RestokListProduk.this, RestokKategori.class);
                startActivity(backToKategori);
                finish();
            }
        });

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backToKategori = new Intent(RestokListProduk.this, RestokKategori.class);
                startActivity(backToKategori);
                finish();
            }
        });
        //-----------------------------akhir backpress--------------------------------------------->

    }

    public void getData(){
        Intent i = getIntent();
        namaKategori = i.getStringExtra("kategori");
    }
}