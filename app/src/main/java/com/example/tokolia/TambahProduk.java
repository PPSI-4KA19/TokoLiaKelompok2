package com.example.tokolia;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;

import android.app.Application;
import android.content.DialogInterface;
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
    EditText stok;
    String intentKategori;
    String intentDesc;


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
        stok = findViewById(R.id.editNumberStok);

        Intent i = getIntent();
        intentKategori = i.getStringExtra("kategori");
        intentDesc = i.getStringExtra("deskripsi");


        //-------------------------UI/UX cosmetic-------------------------------------------------->
        namaProduk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                namaProduk.setBackground(AppCompatResources.getDrawable(getApplicationContext(),
                        R.drawable.background_recycler));
            }
        });

        hargaJual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hargaJual.setBackground(AppCompatResources.getDrawable(getApplicationContext(),
                        R.drawable.background_recycler));
            }
        });
        //-----------------------------END UI/UX cosmetic------------------------------------------>


        //-----------------------------FUNGSI SAVE------------------------------------------------->
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (namaProduk.getText().toString().trim().equals("") || namaProduk.getText() == null) {

                    AlertDialog.Builder alert = new AlertDialog.Builder(TambahProduk.this);
                    alert.setTitle("Nama Produk Kosong");
                    alert.setMessage("Nama produk harus diisi. Tidak boleh kosong.");
                    alert.setCancelable(false);

                    alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            namaProduk.setBackground(AppCompatResources.getDrawable(getApplicationContext(), R.drawable.background_alert));
                            namaProduk.requestFocus();
                        }
                    });

                    AlertDialog alertDialog = alert.create();
                    alertDialog.show();

                } else if (hargaJual.getText().toString().trim().equals("") || hargaJual.getText() == null) {

                    AlertDialog.Builder alert = new AlertDialog.Builder(TambahProduk.this);
                    alert.setTitle("Harga Jual Kosong");
                    alert.setMessage("Harga jual harus diisi. Tidak boleh kosong.");
                    alert.setCancelable(false);

                    alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            hargaJual.setBackground(AppCompatResources.getDrawable(getApplicationContext(),R.drawable.background_alert));
                            hargaJual.requestFocus();
                        }
                    });

                    AlertDialog alertDialog = alert.create();
                    alertDialog.show();

                } else {
                    saveProduk();
                }
            }
        });

        //---------------------------AKHIR FUNGSI SAVE--------------------------------------------->












        //-------------------------BACK PRESS------------------------------------------------------>
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent back = new Intent(TambahProduk.this, ListProduk.class);
                back.putExtra("kategori",intentKategori);
                back.putExtra("deskripsi",intentDesc);
                startActivity(back);
                finish();
            }
        });

        /*getOnBackPressedDispatcher().addCallback(new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                Intent back = new Intent(TambahProduk.this, produkkategori.class);
                startActivity(back);
                finish();
            }
        });*/
        //-----------------AKHIR BACKPRESS--------------------------------------------------------->


    }

    //------------------------METHOD SAVE---------------------------------------------------------->
    public void saveProduk(){

        String nama = namaProduk.getText().toString();
        int jual = Integer.parseInt(hargaJual.getText().toString());
        int modal = Integer.parseInt(hargaModal.getText().toString());
        int jumlahStok = Integer.parseInt(stok.getText().toString());

        Intent intent = new Intent();
        intent.putExtra("namaProduk",nama);
        intent.putExtra("hargaJual",jual);
        intent.putExtra("hargaModal",modal);
        intent.putExtra("stok",jumlahStok);
        setResult(RESULT_OK,intent);
        finish();
    }
    //------------------------------AKHIR METHOD SAVE---------------------------------------------->
}