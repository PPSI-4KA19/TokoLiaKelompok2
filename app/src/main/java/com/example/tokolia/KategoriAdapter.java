package com.example.tokolia;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class KategoriAdapter extends RecyclerView.Adapter<KategoriAdapter.KategoriHolder> {

    private List<Kategori> kategoris = new ArrayList<>();


    @NonNull
    @Override
    public KategoriHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_kategori,
                parent,false);

        return new KategoriHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull KategoriHolder holder, int position) {

        Kategori currentKategori = kategoris.get(position);
        holder.namaKategori.setText(currentKategori.getNamakategori());
        holder.descKategori.setText(currentKategori.getDeskripsi());

    }

    @Override
    public int getItemCount() {
        return kategoris.size();
    }

    public void setKategoris(List<Kategori> kategoris){

        this.kategoris = kategoris;
        notifyDataSetChanged();

    }

    class KategoriHolder extends RecyclerView.ViewHolder{

        TextView namaKategori;
        TextView descKategori;


        public KategoriHolder(@NonNull View itemView) {
            super(itemView);

            namaKategori = itemView.findViewById(R.id.textNamaKategori);
            descKategori = itemView.findViewById(R.id.textDescKategori);

        }
    }
}
