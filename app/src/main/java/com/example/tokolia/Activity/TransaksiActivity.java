package com.example.tokolia.Activity;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.icu.text.DateFormat;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tokolia.Adapter.CartAdapter;
import com.example.tokolia.Entites.Cart;
import com.example.tokolia.Entites.Kasbon;
import com.example.tokolia.Entites.Transaksi;
import com.example.tokolia.Entites.TransaksiProdukCrossRef;
import com.example.tokolia.MainActivity;
import com.example.tokolia.R;
import com.example.tokolia.TokoRepository;
import com.example.tokolia.VM.CartViewModel;
import com.google.android.material.appbar.MaterialToolbar;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TransaksiActivity extends AppCompatActivity {

    TextView textTotalHarga;
    TextView textKembalian;
    Button buttonAddProduk;
    Button buttonCancel;
    Button buttonProsesTransaksi;
    RecyclerView recyclerView;
    EditText inputNominalBayar;
    MaterialToolbar toolbar;
    CartViewModel cartViewModel;

    AlertDialog dialog;

    //-------------------------variabel store intent----------------------------------------------->
    int idProduk;
    String namaProduk;
    int hargaProduk;
    int modalProduk;
    int stokProduk;
    Intent getIntent;
    //--------------------------akhir store intent------------------------------------------------->

    //------------------------------variabel transaksi--------------------------------------------->
    String idTransaksi;
    int total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaksi);


        getData();



        //-------------------------recycler view--------------------------------------------------->

        recyclerView = findViewById(R.id.recyclerProdukTransaksi);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        CartAdapter adapter = new CartAdapter();
        recyclerView.setAdapter(adapter);
        cartViewModel = new ViewModelProvider.AndroidViewModelFactory(getApplication())
                .create(CartViewModel.class);
        cartViewModel.getAllCartInfo().observe(this, new Observer<List<Cart>>() {
            @Override
            public void onChanged(List<Cart> cartList) {
                adapter.setCartList(cartList);
            }
        });

        cartViewModel.getAllKasbon().observe(this, new Observer<List<Kasbon>>() {
            @Override
            public void onChanged(List<Kasbon> kasbons) {
                adapter.setKasbons(kasbons);
            }
        });

        if(idProduk != 0){
            Cart cartBaru = new Cart(idProduk, namaProduk,hargaProduk,1,stokProduk);
            cartViewModel.insertCart(cartBaru);
        }

        adapter.setCartListener(new CartAdapter.CartListener() {
            @Override
            public void onRemove(Cart cart) {
                cartViewModel.deleteCart(cart);
            }

            @Override
            public void onChange(Cart cart) {
                cartViewModel.updateCart(cart);
            }

            @Override
            public void onUpdate(List<Cart> cartList) {
                total = 0;
                for (Cart item : cartList) {
                    total = total + item.getHargaProduk()*item.getQty();
                }
                textTotalHarga.setText(String.format("%,d",total));
            }
        });

        //------------------------akhir recycler view---------------------------------------------->





        //-----------------------------toolbar----------------------------------------------------->
        toolbar = findViewById(R.id.toolbarTransaksi);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBackToMain();
            }
        });

        //---------------------------akhir toolbar------------------------------------------------->

        //setup elemen
        textTotalHarga = findViewById(R.id.textViewTotalHarga);
        textKembalian = findViewById(R.id.textViewKembalian);
        buttonAddProduk = findViewById(R.id.buttonAddProdukToTransaksi);
        buttonCancel = findViewById(R.id.buttonCancelTransaksi);
        buttonProsesTransaksi = findViewById(R.id.buttonProsesTransaksi);
        inputNominalBayar = findViewById(R.id.editTextBayar);

        inputNominalBayar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                textKembalian.setText("0");
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                int kembalian =  Integer.parseInt(s.toString()) - total;
                textKembalian.setText(String.format("%,d",kembalian));
            }
        });


        //----------------------------button tambah produk----------------------------------------->

        buttonAddProduk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toAdd = new Intent(TransaksiActivity.this, TransaksiBukaKategoriActivity.class);
                startActivity(toAdd);
            }
        });

        //------------------------akhir button tambah produk--------------------------------------->





        //-----------------------------button proses----------------------------------------------->

        buttonProsesTransaksi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(TransaksiActivity.this);
                View mView = getLayoutInflater().inflate(R.layout.layout_custom_dialogconfirmtransaksi,null);

                Button buttonPenjualan = mView.findViewById(R.id.buttonPenjualan);
                Button buttonPakai = mView.findViewById(R.id.buttonPemakaian);
                Button buttonHutang = mView.findViewById(R.id.buttonHutang);

                builder.setView(mView);
                dialog = builder.create();
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


                //------------------------pilih penjualan------------------------------------------>
                buttonPenjualan.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        AlertDialog.Builder alert = new AlertDialog.Builder(TransaksiActivity.this);
                        alert.setTitle("Konfirmasi Melanjutkan");
                        alert.setMessage("Sudah yakin dengan transaksi?");

                        alert.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                setChangesAfterTransaction(adapter,"penjualan",null);
                                dialog.dismiss();
                            }
                        });
                        alert.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        dialog = alert.create();
                        dialog.show();
                    }
                });

                //--------------------------akhir pilih penjualan---------------------------------->


                //---------------------------pilih pemakaian sendiri------------------------------->

                buttonPakai.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        AlertDialog.Builder alert = new AlertDialog.Builder(TransaksiActivity.this);
                        alert.setTitle("Konfirmasi Melanjutkan");
                        alert.setMessage("Sudah yakin dengan transaksi?");

                        alert.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                setChangesAfterTransaction(adapter,"pemakaian sendiri",null);
                                dialog.dismiss();
                            }
                        });
                        alert.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        dialog = alert.create();
                        dialog.show();

                    }
                });
                //--------------------------akhir pilih pemakaian sendiri-------------------------->


                //-----------------------------pilih hutang---------------------------------------->
                buttonHutang.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        AlertDialog.Builder pilihAkun = new AlertDialog.Builder(TransaksiActivity.this);
                        View customView = getLayoutInflater()
                                .inflate(R.layout.layout_custom_dialog_pilihkasbon,null);

                        //setup elemen di custom dialog box
                        Spinner spinner = customView.findViewById(R.id.spinnerAkunHutang);
                        Button buttonSave = customView.findViewById(R.id.buttonSimpanPilihanAkunHutang);

                        pilihAkun.setView(customView);
                        dialog = pilihAkun.create();
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                        //----------------------------setup spinner items-------------------------->

                        ArrayList<String> kasbonList = new ArrayList<String>();
                        List<Kasbon> kasbonCont = adapter.getKasbons();
                        for(Kasbon item : kasbonCont){
                            kasbonList.add(item.getPemilik_kasbon());
                        }

                        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(getApplicationContext(),
                                android.R.layout.simple_spinner_item, kasbonList);
                        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinner.setAdapter(spinnerAdapter);

                        dialog.show();

                        //------------------------akhir setup spinner item------------------------->


                        //---------------------------setup tombol simpan--------------------------->

                        buttonSave.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String selectedKasbon = spinner.getSelectedItem().toString();
                                setChangesAfterTransaction(adapter,"hutang",selectedKasbon);
                                int tambahHutang = 0;
                                int tambahSisaHutang = 0;
                                for(Kasbon item : kasbonCont){
                                    if(item.getPemilik_kasbon().equals(selectedKasbon)){
                                        tambahHutang = item.getTotal_hutang() + total;
                                        tambahSisaHutang = item.getSisa_hutang() + total;
                                    }
                                }
                                Kasbon pembaruan = new Kasbon(selectedKasbon,tambahHutang,tambahSisaHutang);
                                cartViewModel.updateKasbon(pembaruan);
                                dialog.dismiss();
                            }
                        });

                        //-------------------------akhir setup tombol simpan----------------------->
                    }
                });

                //--------------------------akhir pilih hutang------------------------------------->

                dialog.show();
            }
        });

        //----------------------------akhir button proses------------------------------------------>





        //-----------------------button cancel + backpress----------------------------------------->

        //button cancel
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBackToMain();
            }
        });

        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                goBackToMain();
            }
        });

        //---------------------akhri button cancel + backpress------------------------------------->

    }


    //----------------------------method untuk kembali--------------------------------------------->
    public void goBackToMain(){
        Intent back = new Intent(TransaksiActivity.this, MainActivity.class);
        startActivity(back);
        finish();
    }
    //-----------------------------akhir method untuk kembali-------------------------------------->



    public void getData(){
        getIntent = getIntent();
        namaProduk = getIntent.getStringExtra("nama_produk");
        idProduk = getIntent.getIntExtra("id_produk",0);
        hargaProduk = getIntent.getIntExtra("harga_produk",0);
        stokProduk = getIntent.getIntExtra("stok_produk",0);
        modalProduk = getIntent.getIntExtra("modal_produk",0);

    }

    public void setChangesAfterTransaction(CartAdapter adapter, String jenisTransaksi, @Nullable String kasbon){
        //Calendar calendar = Calendar.getInstance();

        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        idTransaksi = sdf.format(d);
        SimpleDateFormat sdf2 = new SimpleDateFormat("dd-MM-yyyy");
        String date = sdf2.format(d);

        Transaksi create = new Transaksi(idTransaksi,date,jenisTransaksi,kasbon);
        cartViewModel.insertTransaksi(create);

        List<Cart> newList = adapter.getCartList();
        for(Cart item : newList){
            TransaksiProdukCrossRef crossRef = new TransaksiProdukCrossRef(idTransaksi,item.getIdProduk(),
                    item.getQty(),item.getQty()*item.getHargaProduk());
            cartViewModel.insertCrossRef(crossRef);
            cartViewModel.decreaseProdukStok(item.getIdProduk(),item.getQty());
        }



        Intent toRincian = new Intent(TransaksiActivity.this, RincianOrderActivity.class);
        toRincian.putExtra("jenis",jenisTransaksi);
        toRincian.putExtra("total",total);
        toRincian.putExtra("transaksiId",idTransaksi);
        startActivity(toRincian);
        finish();
    }

    @Override
    protected void onDestroy() {
        cartViewModel.clearCart();
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        if(dialog!=null){
            dialog.dismiss();
        }
        super.onPause();
    }
}

