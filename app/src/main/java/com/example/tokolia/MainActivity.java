package com.example.tokolia;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView textTahun;
    TextView nominalPemasukan;
    TextView nominalPengeluaran;
    TextView nominalHutang;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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