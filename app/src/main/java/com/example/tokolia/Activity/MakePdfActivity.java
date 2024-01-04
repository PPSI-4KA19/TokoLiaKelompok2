package com.example.tokolia.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.tokolia.Entites.Produk;
import com.example.tokolia.Entites.TransaksiProdukCrossRef;
import com.example.tokolia.R;
import com.example.tokolia.TokoRepository;
import com.github.barteksc.pdfviewer.PDFView;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class MakePdfActivity extends AppCompatActivity {

    String fileName;
    String idTransaksi;
    String tanggal;
    Button back;
    Button openInFolder;

    //--------------------------------container as list-------------------------------------------->
    List<Produk> produkList = new ArrayList<>();
    List<TransaksiProdukCrossRef> crossRefs = new ArrayList<>();
    TokoRepository repository;

    PDFView pdfView;
    File file;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_pdf);

        pdfView = findViewById(R.id.pdfView);
        openInFolder = findViewById(R.id.buttonBuka);
        back = findViewById(R.id.buttonBack);



        //get intent - id transaksi, total (jangan lupa di convert string)
        Intent data = getIntent();
        idTransaksi = data.getStringExtra("idTransaksi");
        tanggal = idTransaksi.substring(6,8) + "-" + idTransaksi.substring(4,6)+ "-" + idTransaksi.substring(0,4);
        fileName = "struk_" + idTransaksi + ".pdf";

        repository = new TokoRepository(getApplication());

        crossRefs.addAll(repository.getListCrossRefById(idTransaksi));
        for(TransaksiProdukCrossRef refs : crossRefs){
            produkList.addAll(repository.getListSpesifikProduk(refs.getId_produk()));
        }


        try {
            createPdf();
        } catch (FileNotFoundException e){
            e.printStackTrace();
        }


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        openInFolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent open = new Intent(Intent.ACTION_VIEW, MediaStore.Downloads.EXTERNAL_CONTENT_URI);
                open.setType("*/*");
                startActivity(open);
            }
        });



        //finish();


    }

    private void createPdf() throws FileNotFoundException{
        String pdfPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();
        file = new File(pdfPath, fileName);
        if(file.exists()) {

            pdfView.fromFile(file).load();

            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle("File Ganda");
            alert.setMessage("Struk sudah dengan nama " + fileName +
                    " sudah pernah di download sebelumnya.");
            alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            AlertDialog dialogbox = alert.create();
            dialogbox.show();

        } else {

            OutputStream outputStream = new FileOutputStream(file);

            PdfWriter writer = new PdfWriter(file);
            PdfDocument pdfDocument = new PdfDocument(writer);
            Document document = new Document(pdfDocument);

            float columnWidth[] = {110, 110, 110, 80};
            Table table = new Table(columnWidth);

            //rowNamaToko
            table.addCell(new Cell(1, 4).add(new Paragraph("Toko Lia").setBold().setFontSize(16)
                            .setTextAlignment(TextAlignment.CENTER))
                    .setBorder(Border.NO_BORDER));

            //rowAlamatToko
            table.addCell(new Cell(1, 4).add(new Paragraph("Jalan Muara Baru Gang IX, No 81C RT.21/RW.17 \n " +
                    "Penjaringan, Jakarta Utara, 14440").setItalic()).setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.CENTER));

            //emptyRow
            table.addCell(new Cell(1, 4).add(new Paragraph("")).setBorder(Border.NO_BORDER));

            //rowID
            table.addCell(new Cell().add(new Paragraph("Trans ID :").setBold())
                    .setBorder(Border.NO_BORDER));
            table.addCell(new Cell().add(new Paragraph(idTransaksi))
                    .setBorder(Border.NO_BORDER));
            table.addCell(new Cell(1, 2).add(new Paragraph("")).setBorder(Border.NO_BORDER));

            //rowTanggal
            table.addCell(new Cell().add(new Paragraph("Tanggal :").setBold()).setBorder(Border.NO_BORDER));
            table.addCell(new Cell().add(new Paragraph(tanggal)).setBorder(Border.NO_BORDER));
            table.addCell(new Cell(1, 2).add(new Paragraph("")).setBorder(Border.NO_BORDER));

            //empty rows
            table.addCell(new Cell(1, 4).add(new Paragraph("")).setBorder(Border.NO_BORDER));
            table.addCell(new Cell(1, 4).add(new Paragraph("")).setBorder(Border.NO_BORDER));

            //rowHeaderRincian
            table.addCell(new Cell().add(new Paragraph("Rincian :")).setBorder(Border.NO_BORDER));
            table.addCell(new Cell().add(new Paragraph("")).setBorder(Border.NO_BORDER));
            table.addCell(new Cell().add(new Paragraph("")).setBorder(Border.NO_BORDER));
            table.addCell(new Cell().add(new Paragraph("")).setBorder(Border.NO_BORDER));

            //emptyRow
            table.addCell(new Cell(1, 4).add(new Paragraph("")).setBorder(Border.NO_BORDER)
                    .setBorderBottom(new SolidBorder(1)));

            //rowBodyRincian
            int total = 0;

            for (int i = 0; i < crossRefs.size(); i++) {
                table.addCell(new Cell(1, 4).add(new Paragraph(produkList.get(i).getNama_produk())).setBorder(Border.NO_BORDER));
                //new row

                table.addCell(new Cell().add(new Paragraph("x" + crossRefs.get(i).getQuantity()))
                        .setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.CENTER));
                table.addCell(new Cell().add(new Paragraph(String.format("  @ Rp %,d", produkList.get(i).getHarga_jual())))
                        .setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.LEFT));
                table.addCell(new Cell().add(new Paragraph("")).setBorder(Border.NO_BORDER));
                table.addCell(new Cell().add(new Paragraph(String.format("Rp %,d", crossRefs.get(i).getTotal())))
                        .setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.RIGHT));
                total += crossRefs.get(i).getTotal();
            }

            //rowAkhir
            table.addCell(new Cell(1, 4).add(new Paragraph("")).setBorder(Border.NO_BORDER));
            table.addCell(new Cell(1, 4).add(new Paragraph("")).setBorder(Border.NO_BORDER)
                    .setBorderBottom(new SolidBorder(2)));
            table.addCell(new Cell(1, 4).add(new Paragraph("")).setBorder(Border.NO_BORDER));
            table.addCell(new Cell(1, 2).add(new Paragraph("")).setBorder(Border.NO_BORDER));
            table.addCell(new Cell().add(new Paragraph("Total : ").setBold()).setBorder(Border.NO_BORDER)
                    .setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell().add(new Paragraph(String.format("Rp %,d", total)).setBold()).setBorder(Border.NO_BORDER)
                    .setTextAlignment(TextAlignment.RIGHT));

            table.setHorizontalAlignment(HorizontalAlignment.CENTER);
            table.setMarginTop(50);

            document.add(table);
            document.close();

            Toast.makeText(getApplicationContext(),
                    "Struk berhasil disimpan di folder Download",Toast.LENGTH_SHORT).show();
            finish();
        }


    }


}