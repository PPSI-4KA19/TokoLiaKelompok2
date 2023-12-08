package com.example.tokolia;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

public class produkkategori extends AppCompatActivity {

    GridView gridKategori;
    Button buttonTambahProduk;
    Button buttonTambahKategori;
    TextView namaKategori;
    TextView descKategori;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produkkategori);

        gridKategori = findViewById(R.id.gridKategori);

        buttonTambahKategori = findViewById(R.id.buttonTambahKategori);
        buttonTambahProduk = findViewById(R.id.buttonTambahProduk);

        //untuk ditulis ke db dalam adapter
        //namaKategori = findViewById(R.id.textNamaKategori);
        //descKategori = findViewById(R.id.textDescKategori);

    }
}