package com.example.tokolia;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toolbar;

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