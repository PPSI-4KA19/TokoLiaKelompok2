package com.example.tokolia.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.tokolia.Adapter.ListHistoryAdapter;
import com.example.tokolia.Entites.Transaksi;
import com.example.tokolia.Entites.TransaksiProdukCrossRef;
import com.example.tokolia.MainActivity;
import com.example.tokolia.R;
import com.example.tokolia.VM.KasbonViewModel;
import com.example.tokolia.VM.ProdukViewModel;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.List;

public class HistoryActivity extends AppCompatActivity {

    Spinner spinner;
    RecyclerView recyclerView;
    MaterialToolbar toolbar;
    KasbonViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        //setup elemen
        toolbar = findViewById(R.id.toolbarHistory);
        recyclerView = findViewById(R.id.recyclerTransaksi);
        spinner = findViewById(R.id.spinnerJenis);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent back = new Intent(HistoryActivity.this, MainActivity.class);
                startActivity(back);
                finish();
            }
        });




        //------------------------------recycler view---------------------------------------------->

        ListHistoryAdapter adapter = new ListHistoryAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        viewModel = new ViewModelProvider.AndroidViewModelFactory(getApplication())
                .create(KasbonViewModel.class);
        viewModel.getAllTransaksi().observe(this, new Observer<List<Transaksi>>() {
            @Override
            public void onChanged(List<Transaksi> transaksis) {
                adapter.setTransaksis(transaksis);
            }
        });

        viewModel.getAllCrossRefs().observe(this, new Observer<List<TransaksiProdukCrossRef>>() {
            @Override
            public void onChanged(List<TransaksiProdukCrossRef> transaksiProdukCrossRefs) {
                adapter.setCrossRefs(transaksiProdukCrossRefs);
            }
        });


        //-----------------------------akhir recycler view----------------------------------------->





        //-----------------------------click on recycler item-------------------------------------->
        /*
        adapter.setOnClickItemListener(new ListHistoryAdapter.HistoryListener() {
            @Override
            public void onClick(Transaksi transaksi) {
                Intent toRincian = new Intent(HistoryActivity.this,
                        RincianHistoryActivity.class);
                toRincian.putExtra("idTransaksi",transaksi.getId_transaksi());
                toRincian.putExtra("tanggal",transaksi.getTanggal());
                startActivity(toRincian);
                finish();
            }
        });*/

        adapter.setOnClickItemListener(new ListHistoryAdapter.HistoryListener() {
            @Override
            public void onClick(Transaksi transaksi, String text) {
                Intent toRincian = new Intent(HistoryActivity.this,
                        RincianHistoryActivity.class);
                toRincian.putExtra("idTransaksi",transaksi.getId_transaksi());
                toRincian.putExtra("tanggal",transaksi.getTanggal());
                toRincian.putExtra("nominal",text);
                startActivity(toRincian);
                finish();
            }
        });

        //---------------------------akhir click on recycler item---------------------------------->





        //-----------------------------spinner----------------------------------------------------->

        ArrayAdapter spinnerAdapter = ArrayAdapter.createFromResource(this,R.array.jenis_transaksi
                , android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String select = parent.getItemAtPosition(position).toString();
                if(select.equals("Transaksi Penjualan")){
                    adapter.getFilter().filter("penjualan");
                } else if (select.equals("Transaksi Pemakaian Sendiri")){
                    adapter.getFilter().filter("pemakaian sendiri");
                } else if (select.equals("Transaksi Kasbon")){
                    adapter.getFilter().filter("hutang");
                } else {
                    adapter.getFilter().filter(null);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //------------------------------akhir spinner---------------------------------------------->

    }
}