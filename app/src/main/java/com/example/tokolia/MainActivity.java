package com.example.tokolia;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tokolia.Activity.TransaksiActivity;
import com.google.android.material.appbar.MaterialToolbar;

public class MainActivity extends AppCompatActivity {

    MaterialToolbar toolbar;
    TextView textTahun;
    TextView nominalPemasukan;
    TextView nominalPengeluaran;
    TextView nominalHutang;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //toolbar setup
        toolbar = findViewById(R.id.mainToolbar);
        toolbar.setOverflowIcon(AppCompatResources.getDrawable(this,R.drawable.baseline_menu_24));

        //set navigasi toolber ke activity lain
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                if(item.getItemId() == R.id.dasbor){
                    Toast.makeText(getApplicationContext(),"Anda berada di halaman Dasbor",
                            Toast.LENGTH_SHORT).show();
                } else if(item.getItemId() == R.id.transaksi){
                    //buka activity transaksi
                    Intent bukaTransaksi = new Intent(MainActivity.this, TransaksiActivity.class);
                    startActivity(bukaTransaksi);
                    finish();

                } else if(item.getItemId() == R.id.produk){
                    //buka activity produk kategori
                    Intent bukaProduk = new Intent(MainActivity.this, produkkategori.class);
                    startActivity(bukaProduk);
                    finish();

                } else if(item.getItemId() == R.id.hutang){
                    //buka activity hutang
                } else if(item.getItemId() == R.id.restok){
                    //buka activity restok
                    Intent bukaRestok = new Intent(MainActivity.this, RestokKategori.class);
                    startActivity(bukaRestok);
                    finish();

                }
                return true;
            }
        });

        //text tahun
        textTahun = findViewById(R.id.textViewTahun);

        //text nominal uang
        nominalPemasukan = findViewById(R.id.textViewPemasukan);
        nominalPengeluaran = findViewById(R.id.textViewPengeluaran);
        nominalHutang = findViewById(R.id.textViewHutang);

        //textTahun.setText();
        //nominalPemasukan.setText("" + pemasukan);
        //nominalPengeluaran.setText("" + pengeluaran);
        //nominalHutang.setText("" + hutang);
    }
}