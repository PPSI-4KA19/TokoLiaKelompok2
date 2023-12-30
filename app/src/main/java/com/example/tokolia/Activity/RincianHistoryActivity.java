package com.example.tokolia.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.tokolia.Adapter.RincianTransaksiAdapter;
import com.example.tokolia.Entites.Produk;
import com.example.tokolia.Entites.TransaksiProdukCrossRef;
import com.example.tokolia.R;
import com.example.tokolia.VM.TransaksiViewModel;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.List;

public class RincianHistoryActivity extends AppCompatActivity {

    TextView id;
    TextView date;
    TransaksiViewModel viewModel;
    MaterialToolbar toolbar;
    TextView total;
    RecyclerView recyclerView;
    Button buttonPDF;
    RincianTransaksiAdapter adapter;


    //---------------------------------container intent-------------------------------------------->
    String idTransaksi;
    String tanggal;
    String nominal;
    //-----------------------------akhir container intent------------------------------------------>

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rincian_history);

        //setup elemen
        toolbar = findViewById(R.id.toolbarRincianTransaksi);
        id = findViewById(R.id.textRincianTransaksiIdTransaksi);
        date = findViewById(R.id.textRincianTransaksiTanggal);
        total = findViewById(R.id.textRincianTransaksiTotal);
        recyclerView = findViewById(R.id.recyclerRincianTransaksi);
        buttonPDF = findViewById(R.id.buttonCetak);

        //start
        Intent data = getIntent();
        idTransaksi = data.getStringExtra("idTransaksi");
        tanggal = data.getStringExtra("tanggal");
        nominal = data.getStringExtra("nominal");

        //set text id transaksi + tanggal
        id.setText(idTransaksi);
        date.setText(tanggal);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent back = new Intent(RincianHistoryActivity.this, HistoryActivity.class);
                startActivity(back);
                finish();
            }
        });



        //------------------------------set recycler view------------------------------------------>


        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RincianTransaksiAdapter();
        recyclerView.setAdapter(adapter);

        viewModel = new ViewModelProvider.AndroidViewModelFactory(getApplication())
                .create(TransaksiViewModel.class);

        viewModel.getAllProduk().observe(this, new Observer<List<Produk>>() {
            @Override
            public void onChanged(List<Produk> produks) {
                adapter.setProduks(produks);
            }
        });
        viewModel.getAllCrossRefsById(idTransaksi)
                .observe(this, new Observer<List<TransaksiProdukCrossRef>>() {
            @Override
            public void onChanged(List<TransaksiProdukCrossRef> transaksiProdukCrossRefs) {
                adapter.setCrossRefs(transaksiProdukCrossRefs);
            }
        });
        //-------------------------akhir set recycler view----------------------------------------->



        //--------------------------------set text total------------------------------------------->

        total.setText(nominal);

        //----------------------------------------------------------------------------------------->



        //-----------------------------button PDF-------------------------------------------------->
    }
}