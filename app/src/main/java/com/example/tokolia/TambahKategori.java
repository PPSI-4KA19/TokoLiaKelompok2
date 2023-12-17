package com.example.tokolia;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.appbar.MaterialToolbar;

public class TambahKategori extends AppCompatActivity {

    MaterialToolbar toolbar;
    Button saveButton;
    EditText addNama;
    EditText addDesc;

    //container String
    String nama;
    String desc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_kategori);

        //setup elemen
        toolbar = findViewById(R.id.tambahKategoriToolbar);
        saveButton = findViewById(R.id.buttonSimpanKategori);
        addNama = findViewById(R.id.editTextNamaKategori);
        addDesc = findViewById(R.id.editTextDeskripsiKategori);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backNavigasi();
                finish();
            }
        });

        //backPress
        getOnBackPressedDispatcher().addCallback(new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                backNavigasi();
                finish();
            }
        });


    }

    public void backNavigasi(){
        Intent back = new Intent(TambahKategori.this, produkkategori.class);
        startActivity(back);
    }

}