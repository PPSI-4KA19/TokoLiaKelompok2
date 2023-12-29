package com.example.tokolia;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tokolia.Activity.HistoryActivity;
import com.example.tokolia.Activity.HutangActivity;
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

                Intent intent = new Intent();

                if(item.getItemId() == R.id.dasbor){
                    Toast.makeText(getApplicationContext(),"Anda berada di halaman Dasbor",
                            Toast.LENGTH_SHORT).show();

                } else if(item.getItemId() == R.id.transaksi){
                    //buka activity transaksi
                    intent = new Intent(MainActivity.this, TransaksiActivity.class);
                    startActivity(intent);
                    finish();

                } else if(item.getItemId() == R.id.produk){
                    //buka activity produk kategori
                    intent = new Intent(MainActivity.this, produkkategori.class);
                    startActivity(intent);
                    finish();

                } else if(item.getItemId() == R.id.hutang){
                    //buka activity hutang
                    intent = new Intent(MainActivity.this, HutangActivity.class);
                    startActivity(intent);
                    finish();

                } else if(item.getItemId() == R.id.restok){
                    //buka activity restok
                    intent = new Intent(MainActivity.this, RestokKategori.class);
                    startActivity(intent);
                    finish();

                } else if(item.getItemId() == R.id.history){
                    //buka history
                    intent = new Intent(MainActivity.this, HistoryActivity.class);
                    startActivity(intent);
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