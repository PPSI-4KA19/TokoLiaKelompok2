package com.example.tokolia.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tokolia.Adapter.RincianKasbonAdapter;
import com.example.tokolia.Entites.Kasbon;
import com.example.tokolia.Entites.Transaksi;
import com.example.tokolia.Entites.TransaksiProdukCrossRef;
import com.example.tokolia.R;
import com.example.tokolia.VM.KasbonViewModel;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;
import java.util.List;

public class RincianKasbonActivity extends AppCompatActivity {

    MaterialToolbar toolbar;
    RecyclerView recyclerView;
    TextView sisaHutang;
    TextView totalHutang;
    Button batal;
    Button bayar;

    int total;
    int sisa;

    //container string intent dari HutangActivity
    String namaKasbon;

    //VM + Adapter
    KasbonViewModel kasbonViewModel;
    RincianKasbonAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rincian_kasbon);

        //setup elemen
        toolbar = findViewById(R.id.toolbarRincianKasbon);
        sisaHutang = findViewById(R.id.textViewRincianSisaHutang);
        totalHutang = findViewById(R.id.textViewRincianTotalHutang);
        batal = findViewById(R.id.buttonRincianCancel);
        bayar = findViewById(R.id.buttonRincianBayar);

        //get intent
        Intent getData = getIntent();
        namaKasbon = getData.getStringExtra("namaKasbon");
        total = getData.getIntExtra("totalHutang",0);
        sisa = getData.getIntExtra("sisaHutang",0);



        //-------------------------------toolbar--------------------------------------------------->
        /*
        back arrow set back to HutangActivity + finish()
        set title toolbar
         */
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent back = new Intent(RincianKasbonActivity.this, HutangActivity.class);
                startActivity(back);
                finish();
            }
        });

        toolbar.setTitle(namaKasbon);

        //----------------------------akhir toolbar------------------------------------------------>





        //----------------------------set recycler view-------------------------------------------->

        recyclerView = findViewById(R.id.recyclerRincianKasbon);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new RincianKasbonAdapter();
        recyclerView.setAdapter(adapter);

        kasbonViewModel = new ViewModelProvider.AndroidViewModelFactory(getApplication())
                .create(KasbonViewModel.class);

        kasbonViewModel.getAllCrossRefs().observe(this, new Observer<List<TransaksiProdukCrossRef>>() {
            @Override
            public void onChanged(List<TransaksiProdukCrossRef> transaksiProdukCrossRefs) {
                adapter.setCrossRefs(transaksiProdukCrossRefs);
            }
        });

        kasbonViewModel.getTransaksisOnKasbon(namaKasbon).observe(this, new Observer<List<Transaksi>>() {
            @Override
            public void onChanged(List<Transaksi> transaksis) {
                adapter.setTransaksis(transaksis);
            }
        });

        //-------------------------akhir set recycler view----------------------------------------->





        //-------------------------------set dialog box-------------------------------------------->

        totalHutang.setText(String.format("%,d",total));
        sisaHutang.setText(String.format("%,d",sisa));

        batal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent back = new Intent(RincianKasbonActivity.this, HutangActivity.class);
                startActivity(back);
                finish();
            }
        });

        bayar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(sisa > 0) {
                    AlertDialog.Builder pilih = new AlertDialog.Builder(RincianKasbonActivity.this);
                    View viewPilih = getLayoutInflater().inflate(R.layout.layout_custom_dialog_pilihbayarhutang, null);
                    pilih.setView(viewPilih);

                    TextView totalHut = viewPilih.findViewById(R.id.textViewNominalKonfirmasi);
                    TextView sisaHut = viewPilih.findViewById(R.id.textViewSisaKonfirmasi);
                    TextView an = viewPilih.findViewById(R.id.textViewAN);
                    Button cicil = viewPilih.findViewById(R.id.buttonCicil);
                    Button penuh = viewPilih.findViewById(R.id.buttonPenuh);

                    totalHut.setText(String.format("Rp %,d",total));
                    sisaHut.setText(String.format("Rp %,d",sisa));
                    an.setText("a/n " + namaKasbon);

                    AlertDialog dialog1 = pilih.create();
                    dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialog1.show();

                    //-----------------tombol dialog box pilih cicil / penuh--------------------------->
                    cicil.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog1.dismiss();
                            AlertDialog.Builder bayar = new AlertDialog.Builder(RincianKasbonActivity.this);
                            View mView = getLayoutInflater().inflate(R.layout.layout_custom_dialogbayarhutang, null);

                            EditText input = mView.findViewById(R.id.inputPembayaran);
                            Button kembali = mView.findViewById(R.id.buttonBackBayar);
                            Button lanjut = mView.findViewById(R.id.buttonLanjutBayar);

                            input.setText("0");

                            bayar.setView(mView);
                            AlertDialog dialog2 = bayar.create();
                            dialog2.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                            dialog2.show();

                            kembali.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog2.dismiss();
                                }
                            });

                            lanjut.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    String inputan = input.getText().toString();
                                    if (inputan.equals("") || input.getText() == null
                                            || Integer.parseInt(inputan) == 0) {
                                        input.setBackground(AppCompatResources.getDrawable(getApplicationContext()
                                                , R.drawable.background_alert));
                                        input.requestFocus();
                                    } else {
                                        int sisaBaru = sisa - Integer.parseInt(inputan);
                                        if (sisaBaru > 0) {
                                            Kasbon update = new Kasbon(namaKasbon, total, sisaBaru);
                                            kasbonViewModel.updateKasbon(update);
                                        } else {
                                            Toast.makeText(getApplicationContext()
                                                    , "Pembayaran melebihi sisa hutang", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                    dialog2.dismiss();
                                    Intent goback = new Intent(RincianKasbonActivity.this, HutangActivity.class);
                                    startActivity(goback);
                                    finish();
                                }
                            });

                        }
                    });

                    penuh.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog1.dismiss();

                            AlertDialog.Builder build = new AlertDialog.Builder(RincianKasbonActivity.this);
                            build.setTitle("Melunaskan Semua Hutang");
                            build.setMessage("Anda yakin ingin melunaskan semua hutang atas nama "+ namaKasbon + " ?");
                            build.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Kasbon updateLunas = new Kasbon(namaKasbon, 0, 0);
                                    kasbonViewModel.updateKasbon(updateLunas);

                                    /*
                                    merubah semua transaksi dengan kolom kasbon berisikan nama akun, menjadi null
                                    dan merubah (?) jenis menjadi penjualan (?)
                                     */
                                    List<Transaksi> container = new ArrayList<>();
                                    container = adapter.getTransaksis();
                                    for(Transaksi item : container){
                                        Transaksi baru = new Transaksi(item.getId_transaksi(),
                                                item.getTanggal(),"penjualan",null);
                                        kasbonViewModel.updateTransaksi(baru);
                                    }

                                    dialog.dismiss();
                                    Intent go = new Intent(RincianKasbonActivity.this, HutangActivity.class);
                                    startActivity(go);
                                    finish();
                                }
                            });

                            build.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    dialog1.show();
                                }
                            });
                            //build.setCancelable(false);
                            AlertDialog dialog2 = build.create();
                            dialog2.show();

                        }
                    });

                    //---------akhir tombol dialog box pilih cicil / penuh----------------------------->

                } else {
                    Toast.makeText(getApplicationContext(),
                            "Tidak ada sisa hutang" ,Toast.LENGTH_SHORT).show();
                }

            }
        });
        //--------------------------------akhir set dialog box------------------------------------->
    }
}