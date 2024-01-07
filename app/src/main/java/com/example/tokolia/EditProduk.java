package com.example.tokolia;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.appbar.MaterialToolbar;

/**
 *  halaman edit produk, layout edit dan edit text sama dengan TambahProduk
 *  start : getIntent dari ListProduk -> set di edit text
 *  save : send intent -> ListProduk handle insert via VM
 */
public class EditProduk extends AppCompatActivity {

    EditText namaProduk;
    EditText hargaJual;
    EditText hargaModal;
    EditText stok;
    MaterialToolbar toolbar;

    Button editSave;

    //container check id produk
    int produkId;
    //container detail kategori untuk bisa di send back kalau backpress toolbar
    String kategori;
    String desc;

    //------------------------------container to send intent--------------------------------------->
    int modalEdit = 0;
    int stokEdit = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_produk);

        //setup elemen
        namaProduk = findViewById(R.id.editEditNamaProduk);
        hargaJual = findViewById(R.id.editEditHargaJual);
        hargaModal = findViewById(R.id.editEditHargaModal);
        stok = findViewById(R.id.editEditStok);
        editSave = findViewById(R.id.buttonSimpanEditProduk);

        getData();

        //-------------------------UI UX cosmetics------------------------------------------------->
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
        //----------------------------END UI UX cosmetics------------------------------------------>


        //-------------------------CLICK SIMPAN EDIT----------------------------------------------->
        editSave.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               if (namaProduk.getText().toString().trim().equals("") || namaProduk.getText() == null) {

                   AlertDialog.Builder alert = new AlertDialog.Builder(EditProduk.this);
                   alert.setTitle("Nama Produk Kosong");
                   alert.setMessage("Nama produk harus diisi. Tidak boleh kosong.");
                   alert.setCancelable(false);

                   alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                       @Override
                       public void onClick(DialogInterface dialog, int which) {
                           dialog.cancel();
                           namaProduk.setBackground(AppCompatResources.getDrawable(getApplicationContext(),
                                   R.drawable.background_alert));
                           namaProduk.requestFocus();
                       }
                   });

                   AlertDialog alertDialog = alert.create();
                   alertDialog.show();

               } else if (hargaJual.getText().toString().trim().equals("") || hargaJual.getText() == null) {

                   AlertDialog.Builder alert = new AlertDialog.Builder(EditProduk.this);
                   alert.setTitle("Harga Jual Kosong");
                   alert.setMessage("Harga jual harus diisi. Tidak boleh kosong.");
                   alert.setCancelable(false);

                   alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                       @Override
                       public void onClick(DialogInterface dialog, int which) {
                           dialog.cancel();
                           hargaJual.setBackground(AppCompatResources.getDrawable(getApplicationContext(),
                                   R.drawable.background_alert));
                           hargaJual.requestFocus();
                       }
                   });

                   AlertDialog alertDialog = alert.create();
                   alertDialog.show();

               } else {
                   AlertDialog.Builder alert = new AlertDialog.Builder(EditProduk.this);
                   alert.setTitle("Konfirmasi Perubahan");
                   alert.setMessage("Anda yakin mau menyimpan perubahan?");
                   alert.setCancelable(false);
                   alert.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                       @Override
                       public void onClick(DialogInterface dialog, int which) {
                           dialog.cancel();
                       }
                   });

                   alert.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                       @Override
                       public void onClick(DialogInterface dialog, int which) {
                           if(hargaModal.getText().toString().equals("") || stok.getText().toString().equals("")){
                                updateProduk();
                           } else {
                               modalEdit = Integer.parseInt(hargaModal.getText().toString());
                               stokEdit = Integer.parseInt(stok.getText().toString());
                               updateProduk();
                           }
                       }
                   });

                   AlertDialog alertDialog = alert.create();
                   alertDialog.show();
               }
           }
       });
        //------------------------END SIMPAN EDIT-------------------------------------------------->





        //----------------------back press--------------------------------------------------------->
        /*getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                Intent backToListProduk = new Intent(EditProduk.this, ListProduk.class);
                backToListProduk.putExtra("kategori",kategori);
                backToListProduk.putExtra("deskripsi",desc);
                startActivity(backToListProduk);
                finish();
            }
        });*/

        toolbar = findViewById(R.id.editProdukToolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backToListProduk = new Intent(EditProduk.this, ListProduk.class);
                backToListProduk.putExtra("kategori",kategori);
                backToListProduk.putExtra("deskripsi",desc);
                startActivity(backToListProduk);
                finish();
            }
        });
        //----------------------akhir backpress---------------------------------------------------->

    }


    private void getData(){
        Intent i = getIntent();
        produkId = i.getIntExtra("produkId",-1);
        String nama = i.getStringExtra("namaProduk");
        int jual = i.getIntExtra("hargaJual",0);
        int modal = i.getIntExtra("hargaModal",0);
        int stokProduk = i.getIntExtra("stok",0);
        kategori = i.getStringExtra("kategori");
        desc = i.getStringExtra("deskripsi");

        namaProduk.setText(nama);
        hargaJual.setText(""+jual);
        hargaModal.setText(""+modal);
        stok.setText(""+stokProduk);

    }

    private void updateProduk(){

        Intent intent = new Intent();
        intent.putExtra("namaProduk",namaProduk.getText().toString());
        intent.putExtra("hargaJual",Integer.parseInt(hargaJual.getText().toString()));
        intent.putExtra("hargaModal",modalEdit);
        intent.putExtra("stok",stokEdit);

        if(produkId != -1){
            intent.putExtra("produkId",produkId);
            setResult(RESULT_OK,intent);
            finish();
        }
    }
}