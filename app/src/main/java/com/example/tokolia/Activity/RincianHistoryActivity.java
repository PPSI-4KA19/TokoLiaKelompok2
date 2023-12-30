package com.example.tokolia.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tokolia.Adapter.RincianTransaksiAdapter;
import com.example.tokolia.Entites.Produk;
import com.example.tokolia.Entites.TransaksiProdukCrossRef;
import com.example.tokolia.R;
import com.example.tokolia.VM.TransaksiViewModel;
import com.google.android.material.appbar.MaterialToolbar;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
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

    final static int REQUEST_CODE = 1234;

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

        buttonPDF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                askPermission();
                //createPDF();
            }
        });

        //---------------------------akhir button PDF---------------------------------------------->

    }

    private void askPermission(){
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                REQUEST_CODE);
    }

    /*
    private void createPDF(){

        View view = LayoutInflater.from(this).inflate(R.layout.activity_rincian_history, null);
        DisplayMetrics displayMetrics = new DisplayMetrics();

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.R){
            this.getDisplay().getRealMetrics(displayMetrics);
        } else {
            this.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        }

        view.measure(View.MeasureSpec.makeMeasureSpec(displayMetrics.widthPixels, View.MeasureSpec.EXACTLY),
                View.MeasureSpec.makeMeasureSpec(displayMetrics.heightPixels, View.MeasureSpec.EXACTLY));

        view.layout(0,0, displayMetrics.widthPixels, displayMetrics.heightPixels);
        PdfDocument document = new PdfDocument();

        //int viewWidth = view.getMeasuredWidth();
        //int viewHeight = view.getMeasuredHeight();

        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo
                .Builder(1080, 1920, 1).create();
        PdfDocument.Page page = document.startPage(pageInfo);

        //Canvas
        Canvas canvas = page.getCanvas();
        view.draw(canvas);

        //Finish page
        document.finishPage(page);

        //Download directory
        File downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        String filename = "struk_" + idTransaksi +".pdf";
        File file = new File(downloadsDir,filename);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            document.writeTo(fos);
            document.close();
            fos.close();
            Toast.makeText(this,"Struk berhasil didownload",
                    Toast.LENGTH_SHORT).show();
        } catch (FileNotFoundException e){
            Log.d("Message","Error while writing " + e.toString());
            throw new RuntimeException(e);
        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }*/

}