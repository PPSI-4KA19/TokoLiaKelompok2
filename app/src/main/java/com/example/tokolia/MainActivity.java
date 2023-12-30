package com.example.tokolia;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tokolia.Activity.HistoryActivity;
import com.example.tokolia.Activity.HutangActivity;
import com.example.tokolia.Activity.TransaksiActivity;
import com.example.tokolia.Adapter.MainAdapter;
import com.example.tokolia.Entites.Pencatatan;
import com.example.tokolia.Entites.Produk;
import com.example.tokolia.Entites.Transaksi;
import com.example.tokolia.Entites.TransaksiProdukCrossRef;
import com.example.tokolia.VM.MainViewModel;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    MaterialToolbar toolbar;
    RecyclerView recyclerView;
    MainViewModel viewModel;

    Spinner pilihBulan;
    Spinner pilihTahun;
    Button buttonSelect;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //toolbar setup
        toolbar = findViewById(R.id.mainToolbar);
        toolbar.setOverflowIcon(AppCompatResources.getDrawable(this,R.drawable.baseline_menu_24));

        //set navigasi toolber ke activity lain
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                Intent intent = new Intent();

                if(item.getItemId() == R.id.dasbor){
                    Toast.makeText(getApplicationContext(),"Anda berada di halaman Dasbor",
                            Toast.LENGTH_SHORT).show();

                } else if(item.getItemId() == R.id.transaksi){
                    //buka activity transaksi
                    intent = new Intent(MainActivity.this, TransaksiActivity.class);
                    startActivity(intent);
                    finish();

                } else if(item.getItemId() == R.id.produk){
                    //buka activity produk kategori
                    intent = new Intent(MainActivity.this, produkkategori.class);
                    startActivity(intent);
                    finish();

                } else if(item.getItemId() == R.id.hutang){
                    //buka activity hutang
                    intent = new Intent(MainActivity.this, HutangActivity.class);
                    startActivity(intent);
                    finish();

                } else if(item.getItemId() == R.id.restok){
                    //buka activity restok
                    intent = new Intent(MainActivity.this, RestokKategori.class);
                    startActivity(intent);
                    finish();

                } else if(item.getItemId() == R.id.history){
                    //buka history
                    intent = new Intent(MainActivity.this, HistoryActivity.class);
                    startActivity(intent);
                    finish();
                }
                return true;
            }
        });

        //text tahun
        pilihBulan = findViewById(R.id.spinnerBulan);
        pilihTahun = findViewById(R.id.spinnerTahun);
        buttonSelect = findViewById(R.id.button);

        //----------------------------recycler view------------------------------------------------>

        recyclerView = findViewById(R.id.mainIndex);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        MainAdapter adapter = new MainAdapter();
        recyclerView.setAdapter(adapter);

        viewModel = new ViewModelProvider.AndroidViewModelFactory(getApplication()).create(MainViewModel.class);
        viewModel.getAllIndex().observe(this, new Observer<List<Pencatatan>>() {
            @Override
            public void onChanged(List<Pencatatan> pencatatans) {
                adapter.setPencatatan(pencatatans);
            }
        });
        viewModel.getAllCrossRefs().observe(this, new Observer<List<TransaksiProdukCrossRef>>() {
            @Override
            public void onChanged(List<TransaksiProdukCrossRef> transaksiProdukCrossRefs) {
                adapter.setCrossRefList(transaksiProdukCrossRefs);
            }
        });

        viewModel.getAllProduks().observe(this, new Observer<List<Produk>>() {
            @Override
            public void onChanged(List<Produk> produks) {
                adapter.setProdukList(produks);
            }
        });
        viewModel.getAllTransaksi().observe(this, new Observer<List<Transaksi>>() {
            @Override
            public void onChanged(List<Transaksi> transaksis) {
                adapter.setTransaksiList(transaksis);
            }
        });

        //textTahun.setText();
        //nominalPemasukan.setText("" + pemasukan);
        //nominalPengeluaran.setText("" + pengeluaran);
        //nominalHutang.setText("" + hutang);
    }
}