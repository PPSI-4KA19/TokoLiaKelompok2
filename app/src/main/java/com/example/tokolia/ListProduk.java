package com.example.tokolia;

import androidx.activity.OnBackPressedCallback;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.tokolia.Adapter.ProdukAdapter;
import com.example.tokolia.Entites.Produk;
import com.example.tokolia.VM.ProdukViewModel;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.List;

public class ListProduk extends AppCompatActivity {

    Button addProduk;
    MaterialToolbar toolbar;

    RecyclerView recyclerProduk;

    ProdukAdapter adapter;

    private ProdukViewModel produkViewModel;
    ActivityResultLauncher<Intent> activityResultLauncherAddProduk;
    ActivityResultLauncher<Intent> activityResultLauncherEditProduk;

    ActivityResultLauncher<Intent> activityResultLauncherEditKategori;

    //containers untuk nyimpan message intent
    String namaKategori;
    String descKategori;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_produk);

        toolbar = findViewById(R.id.listProdukToolbar);

        //register activty
        registerActivityAddProduk();
        registerActivityEditProduk();





        //---------------------------setup tampilan recycler--------------------------------------->

        recyclerProduk = findViewById(R.id.recyclerProduk);
        recyclerProduk.setLayoutManager(new LinearLayoutManager(this));

        //catch intent dari kategori
        getData();

        adapter = new ProdukAdapter();
        recyclerProduk.setAdapter(adapter);


        produkViewModel = new ViewModelProvider.AndroidViewModelFactory(getApplication())
                .create(ProdukViewModel.class);
        produkViewModel.getAllProdukOnKategori(namaKategori).observe(this, new Observer<List<Produk>>() {
            @Override
            public void onChanged(List<Produk> produks) {
                //update Recycler View
                adapter.setProduks(produks);
            }
        });

        //-------------------------akhir setup tampilan recycler----------------------------------->






        //---------------------------hapus kategori------------------------------------------------>

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.delete_kategori){

                    AlertDialog.Builder alert = new AlertDialog.Builder(ListProduk.this);
                    alert.setTitle("Menghapus Kategori");
                    alert.setMessage("Anda yakin mau menghapus kategori ini? /n " +
                            "menghapus kategori juga akan menghapus semua produk di dalamnya");
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
                            //delete all produk
                            Intent delete = new Intent(ListProduk.this, produkkategori.class);
                            delete.putExtra("deleteMsg","delete");
                            delete.putExtra("deleteKategori",namaKategori);
                            delete.putExtra("deleteKategoriDesc",descKategori);
                            produkViewModel.deleteProdukOnKategori(namaKategori);
                            startActivity(delete); //delete if error
                            dialog.cancel();
                            finish();
                            Toast.makeText(getApplicationContext(),"Kategori dan isinya berhasil dihapus",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });

                    AlertDialog dialog = alert.create();
                    dialog.show();
                }
                return true;
            }
        });

        //----------------------------------hapus kategori----------------------------------------->





        //----------------------edit produk-------------------------------------------------------->
        adapter.setOnItemClickListener(new ProdukAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Produk produk) {
                Intent intent = new Intent(ListProduk.this, EditProduk.class);
                intent.putExtra("produkId",produk.getId_produk());
                intent.putExtra("namaProduk",produk.getNama_produk());
                intent.putExtra("hargaJual",produk.getHarga_jual());
                intent.putExtra("hargaModal",produk.getHarga_modal());
                intent.putExtra("stok",produk.getStok());
                intent.putExtra("kategori",namaKategori);
                intent.putExtra("deskripsi",descKategori);

                activityResultLauncherEditProduk.launch(intent);
            }
        });
        //----------------------akhir edit produk-------------------------------------------------->





        //--------------------------delete swipe produk-------------------------------------------->

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                AlertDialog.Builder alert = new AlertDialog.Builder(ListProduk.this);
                alert.setTitle("Menghapus Produk");
                alert.setMessage("Anda yakin ingin menghapus produk ini?");
                alert.setCancelable(false);

                alert.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        adapter.notifyItemChanged(viewHolder.getAdapterPosition());
                    }
                });

                alert.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        produkViewModel.deleteProduk(adapter.getProduk(viewHolder.getAdapterPosition()));
                        Toast.makeText(getApplicationContext()
                                ,"Produk " + adapter.getProduk(viewHolder.getAdapterPosition()).getNama_produk() + " berhasil dihapus."
                                , Toast.LENGTH_SHORT).show();
                    }
                });

                AlertDialog alertDialog = alert.create();
                alertDialog.show();
            }
        }).attachToRecyclerView(recyclerProduk);

        //-----------------------akhir delete swipe produk----------------------------------------->





        //-------------------tombol tambah produk-------------------------------------------------->

        addProduk = findViewById(R.id.buttonTambahProduk);
        addProduk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ke activity tambah produk - isi field
                Intent toAddProduk = new Intent(ListProduk.this, TambahProduk.class);
                toAddProduk.putExtra("kategori",namaKategori);
                toAddProduk.putExtra("deskripsi",descKategori);
                activityResultLauncherAddProduk.launch(toAddProduk);

            }
        });

        //-------------------akhir tombol tambah produk-------------------------------------------->

        //----------------all backpress (pencet back hp ataupun back di kiri atas)----------------->
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                Intent backToKategori = new Intent(ListProduk.this, produkkategori.class);
                startActivity(backToKategori);
                finish();
            }
        });

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backToKategori = new Intent(ListProduk.this, produkkategori.class);
                startActivity(backToKategori);
                finish();
            }
        });
        //-----------------------------akhir backpress--------------------------------------------->

    }

    //method untuk terima intent dari kategori
    public void getData(){
        Intent i = getIntent();
        namaKategori = i.getStringExtra("kategori");
        descKategori = i.getStringExtra("deskripsi");

        toolbar.setTitle(namaKategori);
    }


    public void registerActivityAddProduk(){

        activityResultLauncherAddProduk =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                    new ActivityResultCallback<ActivityResult>() {
                        @Override
                        public void onActivityResult(ActivityResult o) {
                            int resultCode = o.getResultCode();
                            Intent data = o.getData();

                            if(resultCode == RESULT_OK && data != null){

                                String namaProduk = data.getStringExtra("namaProduk");
                                int hargaJual = data.getIntExtra("hargaJual",0);
                                int hargaModal = data.getIntExtra("hargaModal",0);
                                int stok = data.getIntExtra("stok",0);

                                Produk produkBaru = new Produk(namaProduk,hargaJual,hargaModal,stok,namaKategori);
                                produkViewModel.insertProduk(produkBaru);

                            }
                        }
                    });

    }

    public void registerActivityEditProduk(){
        activityResultLauncherEditProduk = registerForActivityResult(new ActivityResultContracts
                .StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult o) {
                int resultCode = o.getResultCode();
                Intent data = o.getData();
                if(resultCode == RESULT_OK && data!= null){
                    String namaProduk = data.getStringExtra("namaProduk");
                    int hargaJual = data.getIntExtra("hargaJual",0);
                    int hargaModal = data.getIntExtra("hargaModal",0);
                    int stok = data.getIntExtra("stok",0);

                    Produk produkBaru = new Produk(namaProduk,hargaJual,hargaModal,stok,namaKategori);
                    produkBaru.setId_produk(data.getIntExtra("produkId",-1));
                    produkViewModel.updateProduk(produkBaru);
                }
            }
        });
    }




    /* TODO FILTER METHOD
    //---------------------------------SEARCH VIEW------------------------------------------------->
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_edit, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setQueryHint("Ketikan pencarian");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });

        return true;
    }
    //-----------------------------AKHIR SEARCH VIEW----------------------------------------------->
    */
}