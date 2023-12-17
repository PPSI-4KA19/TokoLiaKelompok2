package com.example.tokolia;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;
import android.window.OnBackInvokedDispatcher;

import com.google.android.material.appbar.MaterialToolbar;

public class produkkategori extends AppCompatActivity {

    MaterialToolbar toolbar;
    GridView gridKategori;
    Button buttonTambahProduk;
    Button buttonTambahKategori;
    TextView namaKategori;
    TextView descKategori;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produkkategori);

        //toolbar setup
        toolbar = findViewById(R.id.kategoriToolbar);
        toolbar.setOverflowIcon(AppCompatResources.getDrawable(this,R.drawable.baseline_menu_24));
        //set navigasi dari overflow
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                if(item.getItemId() == R.id.dasbor){
                    Intent bukaProduk = new Intent(produkkategori.this, MainActivity.class);
                        startActivity(bukaProduk);
                        finish();
                } else if(item.getItemId() == R.id.transaksi){
                    //buka activity transaksi
                } else if(item.getItemId() == R.id.produk){
                    //buka activity produk kategori

                    Toast.makeText(getApplicationContext(),"Anda berada di halaman Produk",
                            Toast.LENGTH_SHORT).show();

                } else if(item.getItemId() == R.id.hutang){
                    //buka activity hutang
                } else if(item.getItemId() == R.id.restok){
                    //buka activity restok
                }
                return true;
            }
        });

        gridKategori = findViewById(R.id.gridKategori);

        //button
        buttonTambahKategori = findViewById(R.id.buttonTambahKategori);
        buttonTambahProduk = findViewById(R.id.buttonTambahProduk);

        buttonTambahKategori.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toAddKategori = new Intent(produkkategori.this, TambahKategori.class);
                startActivity(toAddKategori);
                finish();
            }
        });

        buttonTambahProduk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toAddProduk = new Intent(produkkategori.this, TambahProduk.class);
                startActivity(toAddProduk);
                finish();
            }
        });

        //untuk ditulis ke db dalam adapter
        //namaKategori = findViewById(R.id.textNamaKategori);
        //descKategori = findViewById(R.id.textDescKategori);


        //backpress handling
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                Intent backMain = new Intent(produkkategori.this, MainActivity.class);
                startActivity(backMain);
                finish();
            }
        });

        //back toolbar
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent back = new Intent(produkkategori.this,MainActivity.class);
                startActivity(back);
                finish();
            }
        });
    }


}