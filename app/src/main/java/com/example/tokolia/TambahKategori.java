package com.example.tokolia;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.lifecycle.LiveData;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;
import java.util.List;

/**
 *  halaman tambah kategori :
 *  isi edittext -> send intent ke produkkategori, produkkategori handle insert via VM
 *  string intent : nama kategori, desc kategori
 */
public class TambahKategori extends AppCompatActivity {

    MaterialToolbar toolbar;
    Button saveButton;
    EditText addNama;
    EditText addDesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_kategori);

        //setup elemen
        toolbar = findViewById(R.id.tambahKategoriToolbar);
        saveButton = findViewById(R.id.buttonSimpanKategori);
        addNama = findViewById(R.id.editTextNamaKategori);
        addDesc = findViewById(R.id.editTextDeskripsiKategori);

        //-------------------------UI/UX Cosmetic-------------------------------------------------->
        addNama.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNama.setBackground(AppCompatResources.getDrawable(getApplicationContext(),
                        R.drawable.background_recycler));
            }
        });
        //----------------------END UI/UX Cosmetic------------------------------------------------->


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backNavigasi();
                finish();
            }
        });

        //code untuk tombol simpan
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                TODO buat pencegah add to existing supaya gak crash
                nama kategori tidak boleh kosong
                */
                if (addNama.getText().toString().trim().equals("") || addNama.getText() == null) {
                    //show alert empty
                    AlertDialog.Builder alert = new AlertDialog.Builder(TambahKategori.this);
                    alert.setTitle("Nama Kategori Kosong");
                    alert.setMessage("Nama kategori harus diisi. Tidak boleh kosong.");
                    alert.setCancelable(false);
                    alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            addNama.setBackground(AppCompatResources.getDrawable(getApplicationContext(),
                                    R.drawable.background_alert));
                            addNama.requestFocus();
                        }
                    });
                    AlertDialog alertDialog = alert.create();
                    alertDialog.show();
                } else {
                    saveKategori();
                }
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

    public void saveKategori(){

        String namaKategori = addNama.getText().toString();
        String deskripsiKategori = addDesc.getText().toString();

        Intent passContent = new Intent();
        passContent.putExtra("namaKategori",namaKategori);
        passContent.putExtra("deskripsiKategori",deskripsiKategori);
        setResult(RESULT_OK,passContent);
        finish();

    }

    public void backNavigasi(){
        Intent back = new Intent(TambahKategori.this, produkkategori.class);
        startActivity(back);
    }


}