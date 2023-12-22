package com.example.tokolia;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;

import java.util.List;

public class RestokKategori extends AppCompatActivity {

    RecyclerView recyclerRestokKategori;
    MaterialToolbar toolbar;

    private KategoriViewModel kategoriViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restok_kategori);

        recyclerRestokKategori = findViewById(R.id.recyclerRestokKategori);



        //-----------------------------TOOLBAR----------------------------------------------------->

        toolbar = findViewById(R.id.kategoriRestokToolbar);
        toolbar.setOverflowIcon(AppCompatResources.getDrawable(this,R.drawable.baseline_menu_24));
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                if(item.getItemId() == R.id.dasbor){
                    //buka main activity
                    Intent toMain = new Intent(RestokKategori.this, MainActivity.class);
                    startActivity(toMain);
                    finish();
                } else if(item.getItemId() == R.id.transaksi){
                    //buka activity transaksi
                } else if(item.getItemId() == R.id.produk){
                    //buka activity produk kategori
                    Intent bukaProduk = new Intent(RestokKategori.this, produkkategori.class);
                    startActivity(bukaProduk);
                    finish();
                } else if(item.getItemId() == R.id.hutang){
                    //buka activity hutang
                } else if(item.getItemId() == R.id.restok){
                    //buka activity restok
                    Toast.makeText(getApplicationContext(),"Anda berada di halaman Restok",
                            Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });

        //----------------------AKHIR TOOLBAR------------------------------------------------------>





        //------------------------------RECYCLER VIEW---------------------------------------------->

        recyclerRestokKategori.setLayoutManager(new GridLayoutManager(this,2));

        KategoriAdapter kategoriAdapter = new KategoriAdapter();
        recyclerRestokKategori.setAdapter(kategoriAdapter);

        //view model gridview dari DB
        kategoriViewModel = new ViewModelProvider.AndroidViewModelFactory(getApplication()).create(KategoriViewModel.class);
        kategoriViewModel.getAllKategori().observe(this, new Observer<List<Kategori>>() {
            @Override
            public void onChanged(List<Kategori> kategoris) {
                //update grid view kategori
                kategoriAdapter.setKategoris(kategoris);
            }
        });

        //-----------------------------AKHIR RECYCLER VIEW----------------------------------------->




        //--------------------------open produk dari recycler-------------------------------------->

        kategoriAdapter.setOnItemClickListener(new KategoriAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Kategori kategori) {
                Intent toProduk = new Intent(RestokKategori.this, RestokListProduk.class);
                //kirim info kategori
                toProduk.putExtra("kategori",kategori.getNamakategori());
                toProduk.putExtra("deskripsi",kategori.getDeskripsi());

                startActivity(toProduk);

                //activityResultLauncherOpenProduk.launch(toProduk);

            }
        });

        //----------------------------akhir open produk dari recycler------------------------------>





        //--------------------------------backpress------------------------------------------------>
        //backpress handling
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                Intent backMain = new Intent(RestokKategori.this, MainActivity.class);
                startActivity(backMain);
                finish();
            }
        });

        //back toolbar
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent back = new Intent(RestokKategori.this,MainActivity.class);
                startActivity(back);
                finish();
            }
        });
        //---------------------------akhir backpress----------------------------------------------->
    }
}