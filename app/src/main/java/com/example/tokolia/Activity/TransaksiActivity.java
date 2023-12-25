package com.example.tokolia.Activity;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tokolia.Adapter.CartAdapter;
import com.example.tokolia.Entites.Cart;
import com.example.tokolia.Entites.Transaksi;
import com.example.tokolia.MainActivity;
import com.example.tokolia.R;
import com.example.tokolia.VM.CartViewModel;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

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

    //-------------------------store intent-------------------------------------------------------->

    int idProduk;
    String namaProduk;
    int hargaProduk;
    int modalProduk;
    int stokProduk;
    String kategoriProduk;
    Intent getIntent;
    //--------------------------------------------------------------------------------------------->

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

        if(idProduk != 0){
            Cart cartBaru = new Cart(idProduk, namaProduk,hargaProduk,1,stokProduk);
            cartViewModel.insertCart(cartBaru);
        }

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
                finish();
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




    //--------------------------------initiate transaksi------------------------------------------->

    public void initiateTransaksi(){
        Date today = Calendar.getInstance().getTime();
        Transaksi transaksiBaru = new Transaksi(today,"pembelian",null);
        Toast.makeText(getApplicationContext(), today.toString() ,Toast.LENGTH_SHORT).show();
    }

    //-----------------------------akhir initiate transaksi---------------------------------------->


    public void getData(){
        getIntent = getIntent();
        namaProduk = getIntent.getStringExtra("nama_produk");
        idProduk = getIntent.getIntExtra("id_produk",0);
        hargaProduk = getIntent.getIntExtra("harga_produk",0);
        stokProduk = getIntent.getIntExtra("stok_produk",0);
        modalProduk = getIntent.getIntExtra("modal_produk",0);

    }

    @Override
    protected void onDestroy() {
        cartViewModel.clearCart();
        super.onDestroy();
    }
}

