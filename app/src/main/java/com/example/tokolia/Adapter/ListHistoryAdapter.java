package com.example.tokolia.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tokolia.Entites.Transaksi;
import com.example.tokolia.Entites.TransaksiProdukCrossRef;
import com.example.tokolia.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class ListHistoryAdapter extends RecyclerView.Adapter<ListHistoryAdapter.HistoryViewHolder> implements Filterable {

    HistoryListener listener;
    List<Transaksi> transaksis = new ArrayList<>();
    List<TransaksiProdukCrossRef> crossRefs = new ArrayList<>();
    List<Transaksi> transaksisFull;


    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view_history,parent,false);

        return new HistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryViewHolder holder, int position) {
        Transaksi currentTransaksi = transaksis.get(position);
        holder.idTransaksi.setText("Transaksi #"+currentTransaksi.getId_transaksi());
        holder.tanggal.setText(currentTransaksi.getTanggal());

        int total = 0;
        for(TransaksiProdukCrossRef item : crossRefs){
            if (item.getId_transaksi().equals(currentTransaksi.getId_transaksi())){
                total += item.getTotal();
            }
        }
        holder.nominal.setText(String.format("Rp %,d",total));

    }

    @Override
    public int getItemCount() {
        return transaksis.size();
    }

    public void setTransaksis(List<Transaksi> transaksis){
        this.transaksis = transaksis;
        transaksisFull = new ArrayList<>(transaksis);
        notifyDataSetChanged();
    }

    public void setCrossRefs(List<TransaksiProdukCrossRef> crossRefs){
        this.crossRefs = crossRefs;
        notifyDataSetChanged();
    }

    @Override
    public Filter getFilter() {
        return filterJenis;
    }

    public Filter filterJenis = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Transaksi> filterTransaksi = new ArrayList<>();
            if(constraint==null || constraint.length() == 0){
                filterTransaksi.addAll(transaksisFull);
            } else {
                for(Transaksi item : transaksisFull){
                    if(item.getJenis_transaksi().equals(constraint)){
                        filterTransaksi.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filterTransaksi;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            transaksis.clear();
            transaksis.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    class HistoryViewHolder extends RecyclerView.ViewHolder{

        TextView idTransaksi, nominal, tanggal;

        public HistoryViewHolder(@NonNull View itemView) {
            super(itemView);

            idTransaksi = itemView.findViewById(R.id.textHistoryIdTransaksi);
            nominal = itemView.findViewById(R.id.textHistoryNominal);
            tanggal = itemView.findViewById(R.id.textHistoryTanggalTransaksi);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if(listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onClick(transaksis.get(position),nominal.getText().toString());
                    }
                }
            });
        }
    }

    public void setOnClickItemListener(HistoryListener listener){
        this.listener = listener;
    }

    public interface HistoryListener{
        public void onClick(Transaksi transaksi,String text);
    }

}
