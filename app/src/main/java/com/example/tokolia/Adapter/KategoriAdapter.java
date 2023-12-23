package com.example.tokolia.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tokolia.Entites.Kategori;
import com.example.tokolia.R;

import java.util.ArrayList;
import java.util.List;

public class KategoriAdapter extends RecyclerView.Adapter<KategoriAdapter.KategoriHolder> {

    private List<Kategori> kategoris = new ArrayList<>();
    private OnItemClickListener listener;

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

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int position = getAdapterPosition();
                    if(listener != null && position != RecyclerView.NO_POSITION){
                        listener.onItemClick(kategoris.get(position));
                    }

                }
            });
        }
    }

    public interface OnItemClickListener{
        void onItemClick(Kategori kategori);

    }

    public void setOnItemClickListener(OnItemClickListener listener){

        this.listener = listener;

    }
}
