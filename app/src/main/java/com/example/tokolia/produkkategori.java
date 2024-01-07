package com.example.tokolia;

import androidx.activity.OnBackPressedCallback;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
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
import android.widget.Toast;

import com.example.tokolia.Activity.TransaksiActivity;
import com.example.tokolia.Adapter.KategoriAdapter;
import com.example.tokolia.Entites.Kategori;
import com.example.tokolia.VM.KategoriViewModel;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.List;

/**
    Halaman kategori pada fitur Produk :
    1. menampilkan kategori (recycler view, grid layout span 2)
    2. klik kategori untuk melihat produk pada kategori (adapter.setOnClick method)
    3. tombol add kategori (button.setOnClick method)
 */
public class produkkategori extends AppCompatActivity {


    private KategoriViewModel kategoriViewModel;
    MaterialToolbar toolbar;
    Button buttonTambahKategori;

    //-------------launcher untuk send data-------------------------------------------------------->
    ActivityResultLauncher<Intent> activityResultLauncherAddKategori;
    //--------------------------------------------------------------------------------------------->

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produkkategori);

        //register activity
        registerActivityAddKategori();


        //----------------------------------toolbar setup------------------------------------------>
        toolbar = findViewById(R.id.kategoriToolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent back = new Intent(produkkategori.this,MainActivity.class);
                startActivity(back);
                finish();
            }
        });
        //----------------------------akhir toolbar setup------------------------------------------>





        //-----------------menampilkan kategori ke view-------------------------------------------->
        RecyclerView recyclerKategori = findViewById(R.id.recyclerKategori);
        recyclerKategori.setLayoutManager(new GridLayoutManager(this,2));

        KategoriAdapter kategoriAdapter = new KategoriAdapter();
        recyclerKategori.setAdapter(kategoriAdapter);


        //view model gridview dari DB
        kategoriViewModel = new ViewModelProvider.AndroidViewModelFactory(getApplication()).create(KategoriViewModel.class);
        kategoriViewModel.getAllKategori().observe(this, new Observer<List<Kategori>>() {
            @Override
            public void onChanged(List<Kategori> kategoris) {
                //update grid view kategori
                kategoriAdapter.setKategoris(kategoris);

            }
        });





        //--------------------------DELETE KATEGORI------------------------------------------------>
        /*
            check intent deleteMsg dari ListProduk. Intent code deleteMsg cuma akan kembali kalau
            di ListProduk Activity di klik tombol delete (logo trash)
         */
        Intent checkMsg = getIntent();
        String deleteMsg = checkMsg.getStringExtra("deleteMsg");
        String deleteKategori = checkMsg.getStringExtra("deleteKategori");
        String deleteKategoriDesc = checkMsg.getStringExtra("deleteKategoriDesc");

        Kategori hapus = new Kategori(deleteKategori,deleteKategoriDesc);
        kategoriViewModel.deleteKategori(hapus);
        //---------------------------AKHIR DELETE KATEGORI----------------------------------------->

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
        //------------------akhir open list produk dalam kategori---------------------------------->



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
        //---------------------akhir tombol tambah------------------------------------------------->





        //---------------------------back press---------------------------------------------------->

        //backpress handling
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                Intent backMain = new Intent(produkkategori.this, MainActivity.class);
                startActivity(backMain);
                finish();
            }
        });

        //-------------------------akhir backpress------------------------------------------------->
    }


    /*
        registerActivityAddKategori :
        request result dari activity = TambahKategori
     */
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
                            kategoriViewModel.insertKategori(kategori);
                        }
                    }
                });

    }

}