package com.example.tokolia.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.tokolia.MainActivity;
import com.example.tokolia.R;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class RincianOrderActivity extends AppCompatActivity {

    TextView jenis;
    TextView waktu;
    TextView tanggal;
    TextView jumlah;
    TextView transaksiId;
    MaterialToolbar toolbar;
    MaterialButton buttonDownload;

    //---------------------------------container intent-------------------------------------------->
    String jenisTrans;
    int total;
    String transId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rincian_order);

        //setup elemen
        jenis = findViewById(R.id.textViewRincianJenisTransaksi);
        waktu = findViewById(R.id.textViewRincianWaktuTransaksi);
        tanggal = findViewById(R.id.textViewRincianTanggalTransaksi);
        jumlah = findViewById(R.id.textViewRincianJumlahTransaksi);
        transaksiId = findViewById(R.id.textViewRincianId);

        toolbar = findViewById(R.id.toolbarRincian);
        buttonDownload = findViewById(R.id.buttonDownload);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backToMain();
            }
        });

        Intent getData = getIntent();
        if(getData != null) {
            jenisTrans = getData.getStringExtra("jenis");
            total = getData.getIntExtra("total", 0);
            transId = getData.getStringExtra("transaksiId");
        }

        //-------------------------------put text ke text view------------------------------------->

        Date d = new Date();
        SimpleDateFormat toTime = new SimpleDateFormat("HH:mm:ss");
        String textWaktu = toTime.format(d);
        jenis.setText(jenisTrans);
        waktu.setText(textWaktu);
        tanggal.setText(DateFormat.getDateInstance(DateFormat.DEFAULT).format(Calendar.getInstance().getTime()).toString());
        jumlah.setText(String.format("Rp %,d",total));
        transaksiId.setText("Transaksi #" + transId);

        //----------------------------akhir put text ke text view---------------------------------->





        //TODO button download
        //------------------------------button download-------------------------------------------->



        //--------------------------akhir button download------------------------------------------>


    }

    public void backToMain(){
        Intent intent = new Intent(RincianOrderActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

}