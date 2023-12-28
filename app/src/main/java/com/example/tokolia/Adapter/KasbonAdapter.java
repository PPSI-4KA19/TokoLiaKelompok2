package com.example.tokolia.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tokolia.Entites.Kasbon;
import com.example.tokolia.R;

import java.util.ArrayList;
import java.util.List;

public class KasbonAdapter extends RecyclerView.Adapter<KasbonAdapter.KasbonViewHolder> {

    List<Kasbon> kasbons = new ArrayList<>();
    KasbonListener listener;

    @NonNull
    @Override
    public KasbonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view_kasbon, parent,false);

        return new KasbonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull KasbonViewHolder holder, int position) {
        Kasbon currentKasbon = kasbons.get(position);
        holder.namaAkun.setText(currentKasbon.getPemilik_kasbon());
        holder.totalHutang.setText(String.format("Rp %,d",currentKasbon.getTotal_hutang()));
        holder.sisaHutang.setText(String.format("%,d",currentKasbon.getSisa_hutang()));
    }

    @Override
    public int getItemCount() {
        return kasbons.size();
    }

    public void setKasbons(List<Kasbon> listKasbon){
        this.kasbons = listKasbon;
        notifyDataSetChanged();
    }

    public Kasbon getKasbon(int position){
        return kasbons.get(position);
    }


    class KasbonViewHolder extends RecyclerView.ViewHolder{

        private TextView namaAkun, totalHutang, sisaHutang;
        private Button buttonDetail;


        public KasbonViewHolder(@NonNull View itemView) {
            super(itemView);

            namaAkun = itemView.findViewById(R.id.textViewNamaAkun);
            totalHutang = itemView.findViewById(R.id.textViewNilaiHutang);
            sisaHutang = itemView.findViewById(R.id.textViewSisaHutang);
            buttonDetail = itemView.findViewById(R.id.buttonDetail);

            buttonDetail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if(listener!=null && position != RecyclerView.NO_POSITION){
                        listener.OnClick(kasbons.get(position));
                    }
                }
            });


        }
    }

    public void setKasbonListener(KasbonListener listener){
        this.listener = listener;
    }

    public interface KasbonListener{
        public void OnClick(Kasbon kasbon);
    }

}
