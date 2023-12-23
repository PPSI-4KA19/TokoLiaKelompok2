package com.example.tokolia.Activity;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.tokolia.MainActivity;
import com.example.tokolia.R;
import com.google.android.material.appbar.MaterialToolbar;

public class TransaksiActivity extends AppCompatActivity {

    TextView textTotalHarga;
    TextView textKembalian;
    Button buttonAddProduk;
    Button buttonCancel;
    Button buttonProsesTransaksi;
    RecyclerView recyclerView;
    EditText inputNominalBayar;
    MaterialToolbar toolbar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaksi);

        //-----------------------------toolbar----------------------------------------------------->
        toolbar = findViewById(R.id.toolbarTransaksi);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBackToMain();
            }
        });

        //---------------------------akhir toolbar------------------------------------------------->

        //setup elemen
        textTotalHarga = findViewById(R.id.textViewTotalHarga);
        textKembalian = findViewById(R.id.textViewKembalian);
        buttonAddProduk = findViewById(R.id.buttonAddProdukToTransaksi);
        buttonCancel = findViewById(R.id.buttonCancelTransaksi);
        buttonProsesTransaksi = findViewById(R.id.buttonProsesTransaksi);


        //-----------------------button cancel + backpress----------------------------------------->

        //button cancel
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBackToMain();
            }
        });

        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                goBackToMain();
            }
        });

        //---------------------akhri button cancel + backpress------------------------------------->

    }


    //----------------------------method untuk kembali--------------------------------------------->
    public void goBackToMain(){
        Intent back = new Intent(TransaksiActivity.this, MainActivity.class);
        startActivity(back);
        finish();
    }
    //-----------------------------akhir method untuk kembali-------------------------------------->

}