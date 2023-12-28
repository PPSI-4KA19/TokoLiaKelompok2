package com.example.tokolia.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tokolia.Adapter.KasbonAdapter;
import com.example.tokolia.Entites.Kasbon;
import com.example.tokolia.MainActivity;
import com.example.tokolia.R;
import com.example.tokolia.VM.KasbonViewModel;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.List;

public class HutangActivity extends AppCompatActivity {

    MaterialToolbar toolbar;
    Button addAkun;
    RecyclerView recyclerView;
    KasbonViewModel kasbonViewModel;
    KasbonAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hutang);

        //setup elemen
        toolbar = findViewById(R.id.toolbarKasbon);
        addAkun = findViewById(R.id.buttonAddAkun);
        recyclerView = findViewById(R.id.recyclerKasbon);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HutangActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        //------------------------------setup recycler view---------------------------------------->

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new KasbonAdapter();
        recyclerView.setAdapter(adapter);

        kasbonViewModel = new ViewModelProvider.AndroidViewModelFactory(getApplication())
                .create(KasbonViewModel.class);
        kasbonViewModel.getAllKasbons().observe(this, new Observer<List<Kasbon>>() {
            @Override
            public void onChanged(List<Kasbon> kasbons) {
                adapter.setKasbons(kasbons);
            }
        });

        //--------------------------akhir setup recycler view-------------------------------------->





        //--------------------------tombol detail in recycler view--------------------------------->

        adapter.setKasbonListener(new KasbonAdapter.KasbonListener() {
            @Override
            public void OnClick(Kasbon kasbon) {
                Intent openDetail = new Intent(HutangActivity.this, RincianKasbonActivity.class);
                String nama = kasbon.getPemilik_kasbon();
                int totHut = kasbon.getTotal_hutang();
                int sisHut = kasbon.getSisa_hutang();
                openDetail.putExtra("namaKasbon",nama);
                openDetail.putExtra("totalHutang",totHut);
                openDetail.putExtra("sisaHutang",sisHut);
                startActivity(openDetail);

            }
        });

        //-----------------------akhir tombol detail in recycler view------------------------------>



        //---------------------------------tombol tambah akun-------------------------------------->
        addAkun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder add = new AlertDialog.Builder(HutangActivity.this);
                View viewDialog = getLayoutInflater().inflate(R.layout.layout_custom_dialog_addakun,null);
                add.setView(viewDialog);
                AlertDialog dialog = add.create();
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();

                ImageButton closeDialog = viewDialog.findViewById(R.id.buttonClose);
                Button simpan = viewDialog.findViewById(R.id.buttonSimpanAddKasbon);
                EditText isiNama = viewDialog.findViewById(R.id.editTextIsiNamaKasbon);
                TextView warning = viewDialog.findViewById(R.id.textAddAkunWarning);
                warning.setVisibility(View.INVISIBLE);


                //-------------------------------tombol dialog box--------------------------------->
                closeDialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                simpan.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String namaKasbon = isiNama.getText().toString();
                        if(namaKasbon.equals("")||
                        isiNama.getText() == null){
                            warning.setVisibility(View.VISIBLE);
                            isiNama.setBackground(AppCompatResources
                                    .getDrawable(getApplicationContext(),R.drawable.background_alert));
                            isiNama.requestFocus();
                        } else {
                            Kasbon baru = new Kasbon(namaKasbon,0,0);
                            kasbonViewModel.insertKasbon(baru);
                            dialog.dismiss();
                        }

                    }
                });

                isiNama.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        isiNama.setBackground(AppCompatResources.getDrawable(getApplicationContext(),
                                R.drawable.background_recycler));
                        warning.setVisibility(View.INVISIBLE);
                    }
                });
            }
        });
        //----------------------------akhir tombol tambah akun------------------------------------->



        //--------------------------------delete akun by swipe------------------------------------->

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                AlertDialog.Builder confirm = new AlertDialog.Builder(HutangActivity.this);
                confirm.setTitle("Menghapus Kasbon");
                confirm.setMessage("Anda yakin ingin menghapus kasbon ini? \n" +
                        "Setelah dihapus, anda tidak bisa melihat hutang yang dimiliki oleh kasbon");
                confirm.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        adapter.notifyItemChanged(viewHolder.getAdapterPosition());
                    }
                });

                confirm.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        kasbonViewModel.deleteKasbon(adapter.getKasbon(viewHolder.getAdapterPosition()));
                        Toast.makeText(getApplicationContext()
                                , "Akun Kasbon sudah dihapus", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });
                AlertDialog confirmDelete = confirm.create();
                confirmDelete.show();
            }
        }).attachToRecyclerView(recyclerView);

        //------------------------------akhir delete akun by swipe--------------------------------->

    }
}