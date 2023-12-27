package com.example.tokolia.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;

import com.example.tokolia.R;
import com.google.android.material.appbar.MaterialToolbar;

public class HutangActivity extends AppCompatActivity {

    MaterialToolbar toolbar;
    Button addAkun;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hutang);

        //setup elemen
        toolbar = findViewById(R.id.toolbarKasbon);
        addAkun = findViewById(R.id.buttonAddAkun);
        recyclerView = findViewById(R.id.recyclerKasbon);


    }
}