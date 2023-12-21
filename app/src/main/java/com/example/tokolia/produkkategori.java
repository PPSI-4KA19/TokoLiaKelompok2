package com.example.tokolia;

import androidx.activity.OnBackPressedCallback;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.window.OnBackInvokedDispatcher;

import com.google.android.material.appbar.MaterialToolbar;

import java.util.List;

public class produkkategori extends AppCompatActivity {


    private TokoViewModel tokoViewModel;
    MaterialToolbar toolbar;
    Button buttonTambahKategori;

    //-------------launcher untuk send data-------------------------------------------------------->

    ActivityResultLauncher<Intent> activityResultLauncherAddKategori;
    ActivityResultLauncher<Intent> activityResultLauncherOpenProduk;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produkkategori);

        //register activity
        registerActivityAddKategori();

        //toolbar setup
        toolbar = findViewById(R.id.kategoriToolbar);
        toolbar.setOverflowIcon(AppCompatResources.getDrawable(this,R.drawable.baseline_menu_24));
        //set navigasi dari overflow
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                if(item.getItemId() == R.id.dasbor){
                    Intent bukaProduk = new Intent(produkkategori.this, MainActivity.class);
                        startActivity(bukaProduk);
                        finish();
                } else if(item.getItemId() == R.id.transaksi){
                    //buka activity transaksi
                } else if(item.getItemId() == R.id.produk){
                    //buka activity produk kategori

                    Toast.makeText(getApplicationContext(),"Anda berada di halaman Produk",
                            Toast.LENGTH_SHORT).show();

                } else if(item.getItemId() == R.id.hutang){
                    //buka activity hutang
                } else if(item.getItemId() == R.id.restok){
                    //buka activity restok
                }
                return true;
            }
        });



        //-----------------menampilkan kategori ke view-------------------------------------------->
        RecyclerView recyclerKategori = findViewById(R.id.recyclerKategori);
        recyclerKategori.setLayoutManager(new GridLayoutManager(this,2));

        KategoriAdapter kategoriAdapter = new KategoriAdapter();
        recyclerKategori.setAdapter(kategoriAdapter);

        //view model gridview dari DB
        tokoViewModel = new ViewModelProvider.AndroidViewModelFactory(getApplication()).create(TokoViewModel.class);
        tokoViewModel.getAllKategori().observe(this, new Observer<List<Kategori>>() {
            @Override
            public void onChanged(List<Kategori> kategoris) {
                //update grid view kategori
                kategoriAdapter.setKategoris(kategoris);

            }
        });

        //-----------------end menampilkan kategori------------------------------------------------>

        //-----------------open list produk dalam kategori----------------------------------------->
        kategoriAdapter.setOnItemClickListener(new KategoriAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Kategori kategori) {
                Intent toProduk = new Intent(produkkategori.this, ListProduk.class);
                //kirim info kategori
                toProduk.putExtra("kategori",kategori.getNamakategori());
                toProduk.putExtra("deskripsi",kategori.getDeskripsi());

                startActivity(toProduk);

                //activityResultLauncherOpenProduk.launch(toProduk);

            }
        });






        //-----------------tombol------------------------------------------------------------------>
        //identify button
        buttonTambahKategori = findViewById(R.id.buttonTambahKategori);

        //kalo klik button tambah kategori -->
        buttonTambahKategori.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toAddKategori = new Intent(produkkategori.this, TambahKategori.class);
                activityResultLauncherAddKategori.launch(toAddKategori);
            }
        });






        //------------------------- back press ---------------------------------------------------->



        //backpress handling
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                Intent backMain = new Intent(produkkategori.this, MainActivity.class);
                startActivity(backMain);
                finish();
            }
        });

        //back toolbar
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent back = new Intent(produkkategori.this,MainActivity.class);
                startActivity(back);
                finish();
            }
        });
    }

    public void registerActivityAddKategori(){

        activityResultLauncherAddKategori = registerForActivityResult(new ActivityResultContracts.StartActivityForResult()
                , new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult o) {
                        int resultCode = o.getResultCode();
                        Intent data = o.getData();

                        if(resultCode == RESULT_OK && data!=null){
                            String namaKategori = data.getStringExtra("namaKategori");
                            String descKategori = data.getStringExtra("deskripsiKategori");

                            Kategori kategori = new Kategori(namaKategori,descKategori);
                            tokoViewModel.insertKategori(kategori);
                        }
                    }
                });

    }

    public void registerActivityOpenProduk(){

        activityResultLauncherOpenProduk = registerForActivityResult(new ActivityResultContracts.StartActivityForResult()
                ,new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult o) {


                        }

                });

    }

}