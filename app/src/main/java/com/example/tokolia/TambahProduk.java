package com.example.tokolia;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import com.google.android.material.appbar.MaterialToolbar;

public class TambahProduk extends AppCompatActivity {

    MaterialToolbar toolbar;
    Button saveButton;
    EditText namaProduk;
    EditText hargaJual;
    EditText hargaModal;
    Spinner kategori;

    SpinnerAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_produk);

        //setup elemen
        toolbar = findViewById(R.id.produkToolbar);
        saveButton = findViewById(R.id.buttonSimpanProduk);
        namaProduk = findViewById(R.id.editTextNamaProduk);
        hargaJual = findViewById(R.id.editNumberHargaJual);
        hargaModal = findViewById(R.id.editNumberHargaModal);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent back = new Intent(TambahProduk.this, produkkategori.class);
                startActivity(back);
                finish();
            }
        });

        getOnBackPressedDispatcher().addCallback(new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                Intent back = new Intent(TambahProduk.this, produkkategori.class);
                startActivity(back);
                finish();
            }
        });

        kategori = findViewById(R.id.spinnerKategori);

    }
}