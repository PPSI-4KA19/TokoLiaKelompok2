package com.example.tokolia;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
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

import java.util.ArrayList;
import java.util.List;


/**
    Halaman utama aplikasi :
    1. navigasi ke 6 halaman fitur = produk, transaksi, restok, history, hutang/kasbon, dashboard (this)
    2. tampilan keuangan = pemasukkan, pengeluaran, hutang
 */
public class MainActivity extends AppCompatActivity {

    MaterialToolbar toolbar;
    RecyclerView recyclerView;
    MainViewModel viewModel;

    Spinner pilihTahun;
    Spinner pilihBulan;
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


        //----------------------------------spinner------------------------------------------------>
        pilihBulan = findViewById(R.id.spinnerBulan);
        pilihTahun = findViewById(R.id.spinnerTahun);
        ArrayAdapter adapterBulan = ArrayAdapter.createFromResource(this, R.array.bulan,
                android.R.layout.simple_spinner_item);
        adapterBulan.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        pilihBulan.setAdapter(adapterBulan);

        ArrayAdapter adapterTahun = ArrayAdapter.createFromResource(this, R.array.tahun,
                android.R.layout.simple_spinner_item);
        adapterTahun.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        pilihTahun.setAdapter(adapterTahun);
        //-----------------------------------akhir spinner----------------------------------------->





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

        //------------------------------------set periode------------------------------------------>


        buttonSelect = findViewById(R.id.button);
        buttonSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String month = pilihBulan.getSelectedItem().toString();
                String year = pilihTahun.getSelectedItem().toString();

                if(!year.equals("pilih tahun")
                        && !month.equals("pilih bulan")){
                    viewModel.getAllTransaksi().observe(MainActivity.this, new Observer<List<Transaksi>>() {
                        @Override
                        public void onChanged(List<Transaksi> transaksis) {
                            List<Transaksi> container = new ArrayList<>();
                            for(Transaksi item : transaksis){
                                if (item.getId_transaksi().substring(0,6).equals(year+month)){
                                    container.add(item);
                                }
                            }
                            adapter.setTransaksiList(container);
                        }
                    });
                } else if(!year.equals("pilih tahun")){
                    viewModel.getAllTransaksi().observe(MainActivity.this, new Observer<List<Transaksi>>() {
                        @Override
                        public void onChanged(List<Transaksi> transaksis) {
                            List<Transaksi> container = new ArrayList<>();
                            for(Transaksi item : transaksis){
                                if(item.getId_transaksi().substring(0,4).equals(year)){
                                    container.add(item);
                                }
                            }
                            adapter.setTransaksiList(container);
                        }
                    });
                } else {
                    viewModel.getAllTransaksi().observe(MainActivity.this, new Observer<List<Transaksi>>() {
                        @Override
                        public void onChanged(List<Transaksi> transaksis) {
                            adapter.setTransaksiList(transaksis);
                        }
                    });
                }
            }
        });

        //--------------------------------akhir set periode---------------------------------------->

        //textTahun.setText();
        //nominalPemasukan.setText("" + pemasukan);
        //nominalPengeluaran.setText("" + pengeluaran);
        //nominalHutang.setText("" + hutang);
    }
}