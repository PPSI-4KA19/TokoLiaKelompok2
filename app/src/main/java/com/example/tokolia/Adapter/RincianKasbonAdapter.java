package com.example.tokolia.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tokolia.Entites.Transaksi;
import com.example.tokolia.Entites.TransaksiProdukCrossRef;
import com.example.tokolia.R;

import java.util.ArrayList;
import java.util.List;

public class RincianKasbonAdapter extends RecyclerView.Adapter<RincianKasbonAdapter.RincianKasbonViewHolder> {

    List<Transaksi> transaksis = new ArrayList<>();

    List<TransaksiProdukCrossRef> crossRefs = new ArrayList<>();

    @NonNull
    @Override
    public RincianKasbonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view_rinciankasbon,parent,false);

        return new RincianKasbonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RincianKasbonViewHolder holder, int position) {
        Transaksi currentTransaksi = transaksis.get(position);
        holder.transaksiId.setText(currentTransaksi.getId_transaksi());
        holder.namaKasbon.setText("a/n " + currentTransaksi.getKasbon());
        holder.tanggal.setText(currentTransaksi.getTanggal());

        String id = currentTransaksi.getId_transaksi();
        int total = 0;
        for (TransaksiProdukCrossRef item : crossRefs) {
            if(item.getId_transaksi().equals(id)){
                total += item.getTotal();
            }
        }
        holder.nilaiTransaksi.setText(String.format("Rp %,d",total));


    }

    @Override
    public int getItemCount() {
        return transaksis.size();
    }

    public void setTransaksis(List<Transaksi> transaksis){
        this.transaksis = transaksis;
        notifyDataSetChanged();
    }

    public void setCrossRefs(List<TransaksiProdukCrossRef> crossRefs){
        this.crossRefs = crossRefs;
        notifyDataSetChanged();
    }

    public List<Transaksi> getTransaksis(){
        return transaksis;
    }

    class RincianKasbonViewHolder extends RecyclerView.ViewHolder{

        TextView transaksiId, tanggal, nilaiTransaksi, namaKasbon;
        public RincianKasbonViewHolder(@NonNull View itemView) {
            super(itemView);

            transaksiId = itemView.findViewById(R.id.textViewRincianIdTransaksi);
            tanggal = itemView.findViewById(R.id.textViewRincianTanggalKasbon);
            nilaiTransaksi = itemView.findViewById(R.id.textViewRincianNilaiTransaksi);
            namaKasbon = itemView.findViewById(R.id.textViewRincianNamaKasbon);


        }
    }
}
